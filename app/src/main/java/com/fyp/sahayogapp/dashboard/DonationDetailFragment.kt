package com.fyp.sahayogapp.dashboard

import android.app.DatePickerDialog
import android.icu.text.CaseMap
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.utils.Conts.DONATION_DATA
import com.fyp.sahayogapp.utils.DateFormatter.convertDate
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.dashboard.model.AcceptDonation
import com.fyp.sahayogapp.dashboard.model.HospitalInfoResponse
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.utils.Conts.DONOR
import com.fyp.sahayogapp.utils.Conts.HOSPITAL
import com.fyp.sahayogapp.utils.DateFormatter.getDateParsed
import com.fyp.sahayogapp.utils.PreferenceHelper.getRoleID
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [DonationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationDetailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var data: DonationRequestModel? = null


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
    private lateinit var apointmentBtn : ImageButton
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
            Navigation.findNavController(backbtn).navigate(R.id.action_donationDetailFragment_to_nav_home)
        }
        call.setOnClickListener {


            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:${data?.user_phone}")
            startActivity(callIntent)
        }
        donateBtn.setOnClickListener { 
            if (userRole!= DONOR){
                showAlert("Unauthorized","Only User who are donor can accept a Request")
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

    private fun initView(view: View) {

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
               showAlert("Success",it.message)
           }
           else{
               dismissProgress()
               showAlert("Sorry",it?.message)
           }
       })
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