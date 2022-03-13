package com.fyp.sahayogapp.dashboard.frags

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.model.AcceptDonation
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.utils.Conts.CALL_PHONE
import com.fyp.sahayogapp.utils.Conts.DESTINATION_LAT_LONG
import com.fyp.sahayogapp.utils.Conts.DONATION_DATA
import com.fyp.sahayogapp.utils.Conts.DONOR
import com.fyp.sahayogapp.utils.Conts.LOCATION_REQUEST
import com.fyp.sahayogapp.utils.Conts.MY_LOCATION_LAT_LONG
import com.fyp.sahayogapp.utils.DateFormatter.convertDate
import com.fyp.sahayogapp.utils.DateFormatter.getDateParsed
import com.fyp.sahayogapp.utils.PreferenceHelper.getRoleID
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [DonationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationDetailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var data: DonationRequestModel? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var remainingUnitTV: CustomTextView
    private lateinit var bloodGroup:CustomTextView
    private lateinit var donationType:CustomTextView
    private lateinit var donationStatus:CustomTextView
    private lateinit var userName:CustomTextView
    private lateinit var call:ImageButton
    private lateinit var requiredAmount:CustomTextView
    private lateinit var patientName:CustomTextView
    private lateinit var venueName:CustomTextView
    private lateinit var requestDate:CustomTextView
    private lateinit var dateTill: CustomTextView
    private lateinit var address: CustomTextView
    private lateinit var openTime: CustomTextView
    private lateinit var closeTime: CustomTextView
    private lateinit var backbtn: ImageButton
    private lateinit var mapBtn: ImageButton
    private lateinit var apointmentBtn: ImageButton
    private lateinit var message : TextView
    private lateinit var requestViewModel: RequestViewModel
    private lateinit var donateBtn : Button
    private val userRole = getUserRole()
    private val typeID = getRoleID()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable(DONATION_DATA) as DonationRequestModel?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donation_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestViewModel = ViewModelProvider(this).get(RequestViewModel::class.java)
        initView(view)
        acceptDonationObserver()


        backbtn.setOnClickListener {
            Navigation.findNavController(backbtn)
                .navigate(R.id.action_donationDetailFragment_to_nav_home)
        }


        mapBtn.setOnClickListener {
                requestLocationPermission()
        }
        call.setOnClickListener {
            requestCallPermission()
        }

        donateBtn.setOnClickListener {
            if (userRole != DONOR) {
                showAlert("Unauthorized", "Only User who are donor can accept a Request")
                return@setOnClickListener
            }

            acceptDonation()
        }
        apointmentBtn.setOnClickListener {

            val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())


            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Appointment Date")
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.show(parentFragmentManager, "")
            datePicker.addOnPositiveButtonClickListener {
                val appointmentDate = it?.let { it1 -> Date(it1) }
                val dateString = getDateParsed(
                    appointmentDate.toString(),
                    "EEE MMM dd hh:mm:ss 'GMT'Z yyyy"
                )!!
                Toast.makeText(context, dateString, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun requestCallPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {

            makeCall()
        } else {


            requestPermissions(
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE)

        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {

            openMaps()
        } else {


            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST)

        }
    }

    private fun makeCall() {

        showAlert("Confirm",
            "Do you want to call ${data?.user_name} on ${data?.user_phone} ?",
            "Call",
            DialogInterface.OnClickListener { dialog, which ->
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${data?.user_phone}")
                startActivity(callIntent)
            })

    }

    private fun initView(view: View) {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        remainingUnitTV = view.findViewById(R.id.remainingUnitTV)
        bloodGroup = view.findViewById(R.id.bloodType)
        donationType = view.findViewById(R.id.requestTypeTV)
        donationStatus = view.findViewById(R.id.requestStatusTV)
        userName = view.findViewById(R.id.issuedByTv)
        patientName = view.findViewById(R.id.patientTV)
        venueName = view.findViewById(R.id.hospitalName)
        requiredAmount = view.findViewById(R.id.totalUnitTV)
        dateTill = view.findViewById(R.id.validTV)
        requestDate = view.findViewById(R.id.issuedTV)
        address = view.findViewById(R.id.addressTV)
        openTime = view.findViewById(R.id.openTV)
        closeTime= view.findViewById(R.id.closeTv)
        message = view.findViewById(R.id.messageTV)
        backbtn = view.findViewById(R.id.backBtn)
        call = view.findViewById(R.id.callBtn)
        mapBtn = view.findViewById(R.id.mapBtn)
        donateBtn = view.findViewById(R.id.donate)
        apointmentBtn = view.findViewById(R.id.appointment)
        remainingUnitTV.text = data?.remaining_unit
        bloodGroup.text = data?.blood_group

        if(data?.message==null ||data?.message==""){
            message.visibility = View.GONE

        }
        else{
            message.visibility= View.VISIBLE
            message.text = data?.message
        }
        if (data?.donation_status =="false"){
            donationStatus.text = "In Progress"
        }else{
            donationStatus.text = "Completed"
        }

        donationType.text = data?.donation_type
        userName.text = data?.user_name
        venueName.text = data?.venue_name
        patientName.text = data?.patient_name
        dateTill.text = convertDate(data?.date_till.toString())
        requestDate.text = convertDate(data?.request_date.toString())
        requiredAmount.text ="Total Units\n"+data?.required_amount
        val addressText = getAddress(data?.latitude!!.toDouble(), data?.longitude!!.toDouble() )
//        address.text = data?.latitude + data?.longitude
        address.text = addressText
        openTime.text = data?.open_time
        closeTime.text = data?.close_time
    }


    fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

   private fun acceptDonation(){
       showProgress()
        val acceptDonation = AcceptDonation(data?.donation_id.toString(),typeID.toString(),"","","" )
        
        requestViewModel.acceptRequest(acceptDonation)
    }
    
   private fun acceptDonationObserver(){
       requestViewModel.acceptDonationObserver().observe(viewLifecycleOwner,{
           if (it==null){
               dismissProgress()
               showAlert("Sorry","Server Request Error!!")
               
           }
           if (it?.code=="200"){

               dismissProgress()
               showAlert("Success", it.message)
           } else {
               dismissProgress()
               showAlert("Sorry", it?.message)
           }
       })
   }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    ActivityCompat.requestPermissions(requireActivity()!!,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        CALL_PHONE)
                } else {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Permission Required")
                        .setMessage("This app requires access to Phone Call to proceed. Would you like to open setting and grant permission?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Open Settings",
                            DialogInterface.OnClickListener { dialog, which ->
                                startActivityForResult(Intent().apply {
                                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    data = Uri.fromParts(
                                        "package",
                                        requireActivity().packageName,
                                        null
                                    )
                                }, CALL_PHONE)
                            })
                        .show()

                }

            }
        }
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMaps()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(requireActivity()!!,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_REQUEST)
                } else {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Permission Required")
                        .setMessage("This app requires access to Location Access to proceed. Would you like to open setting and grant permission?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Open Settings",
                            DialogInterface.OnClickListener { dialog, which ->
                                startActivityForResult(Intent().apply {
                                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    data = Uri.fromParts(
                                        "package",
                                        requireActivity().packageName,
                                        null
                                    )
                                }, LOCATION_REQUEST)
                            })
                        .show()

                }

            }
        }
    }

    private fun openMaps() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    var destination = "${data?.latitude},${data?.longitude}"
                    var myLocation = "${it.latitude},${it.longitude}"
                    var bundle = Bundle()
                    bundle.putString(DESTINATION_LAT_LONG, destination)
                    bundle.putString(MY_LOCATION_LAT_LONG, myLocation)
                    Navigation.findNavController(mapBtn)
                        .navigate(R.id.action_donationDetailFragment_to_mapViewFragment, bundle)
                }
            }
        }
        else{

            Toast.makeText(requireContext(), "Location Fetch failed", Toast.LENGTH_SHORT).show()
        }
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment DonationDetailFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            DonationDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

}