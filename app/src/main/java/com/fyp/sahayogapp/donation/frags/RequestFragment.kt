package com.fyp.sahayogapp.donation.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.model.VenueData
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.utils.Conts
import com.fyp.sahayogapp.utils.DateFormatter
import com.fyp.sahayogapp.utils.DateFormatter.convertToDBDate
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var bloodRadio: RadioButton
    private lateinit var plateletRadio: RadioButton
    private lateinit var backBtn: ImageButton
    private lateinit var bloodGroup: AutoCompleteTextView
    private lateinit var reason: AutoCompleteTextView
    private lateinit var pints: AutoCompleteTextView
    private lateinit var venueDD: AutoCompleteTextView
    private lateinit var message: EditText
    private lateinit var patientName: EditText
    private lateinit var date: EditText
    private lateinit var addVenue: TextView
    var venueList = mutableListOf<VenueData>()
    var dbDate = ""
    var venueID : String? = null
    var venueName = ""
    var venueContact = ""
    var workStart = ""
    var workEnd = ""
    var latitude = ""
    var longitude = ""
    val userID = getUserId()
    val userRole = getUserRole()
    private lateinit var requestViewModel: RequestViewModel


    val nameList: ArrayList<String> = ArrayList()

    private lateinit var continueBtn: Button
    private var requestType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        requestViewModel = ViewModelProvider(this).get(RequestViewModel::class.java)
        postRequestObserver()
        val bloods = resources.getStringArray(R.array.blood_group)
        val bloodGroupAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, bloods)
        bloodGroup.setAdapter(bloodGroupAdapter)

        val reasons = resources.getStringArray(R.array.reasons)
        val reasonAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, reasons)
        reason.setAdapter(reasonAdapter)

        val units = resources.getStringArray(R.array.units)
        val unitsAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, units)
        pints.setAdapter(unitsAdapter)


        backBtn.setOnClickListener {
            activity?.finish()
        }


        getVenue()


        val venueAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, nameList)
        venueDD.setAdapter(venueAdapter)

        venueDD.setOnItemClickListener { parent, view, position, id ->
            venueID = venueList[position].venue_id
            Toast.makeText(requireContext(), venueID, Toast.LENGTH_SHORT).show()
        }

        date.setOnClickListener {
            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())


            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Required Date")
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.show(parentFragmentManager, "")
            datePicker.addOnPositiveButtonClickListener {
                val appointmentDate = it?.let { it1 -> Date(it1) }

                val dateString = DateFormatter.getDateParsed(
                    appointmentDate.toString(),
                    "EEE MMM dd hh:mm:ss 'GMT'Z yyyy"
                )!!
                dbDate = convertToDBDate(appointmentDate.toString())
                Log.i("TAG", "onViewCreated: "+dbDate)

                date.setText(dateString)

            }
        }

        addVenue.setOnClickListener {
            var addVenueDialog = AddVenueDialogFragment()
            addVenueDialog.show(parentFragmentManager, "addVenue")
            addVenueDialog.setListener(object : AddVenueDialogFragment.OnInputListener {
                override fun sendInput(venueInfo: VenueData?) {
                    Log.i("TAG", "sendInput: " + venueInfo)
                    venueName = venueInfo!!.venue_name
                    venueContact = venueInfo.venue_contact
                    workStart = venueInfo.open_time
                    workEnd = venueInfo.close_time
                     latitude = venueInfo.latitude
                    longitude = venueInfo.longitude

                    venueDD.setText(venueName)
                }

            })
        }

        continueBtn.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_blood -> {
                    requestType = bloodRadio.text.toString()
                }
                R.id.radio_platelets -> {
                    requestType = plateletRadio.text.toString()
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Please select the request type",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


            }

            postRequest()

        }


    }

    private fun postRequest() {
        if (userRole == Conts.DONOR) {
            postDonorRequest()
        }
        if (userRole == Conts.HOSPITAL) {
            postHospitalRequest()
        }

    }


    private fun postHospitalRequest() {

    }


    private fun getVenue() {
        requestViewModel.getVenueObserver().observe(requireActivity(), Observer<List<VenueData>> {
            if (it == null) {
//                dismissProgress()
//                showAlert("Sorry!","No results Found!")
            } else {


                venueList = it.toMutableList()

                for (name in venueList) {
                    nameList.add(name.venue_name)
                }

            }
        })
        requestViewModel.getVenueList()
    }


    private fun initView(view: View) {
        radioGroup = view.findViewById(R.id.typeRadio)
        bloodRadio = view.findViewById(R.id.radio_blood)
        plateletRadio = view.findViewById(R.id.radio_platelets)
        continueBtn = view.findViewById(R.id.continueBtn)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        reason = view.findViewById(R.id.reasonDD)
        pints = view.findViewById(R.id.units)
        venueDD = view.findViewById(R.id.hospitalDD)
        backBtn = view.findViewById(R.id.backBtn)
        message = view.findViewById(R.id.messageET)
        patientName = view.findViewById(R.id.patientNameET)
        date = view.findViewById(R.id.requiredDate)
        addVenue = view.findViewById(R.id.addVenue)


    }

    private fun postRequestObserver() {
        requestViewModel.postDonationObserver().observe(requireActivity(), Observer<APIResponse> {
            if (it == null) {
                showAlert("Sorry", "Server Request Failed")
            }
            if (it.code == "200") {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                showAlert("Sorry", it.message)
            }

        })

    }

    private fun postDonorRequest() {

        var postDonation = DonationRequestModel(
            null,
            bloodGroup.text.toString(),
            requestType,
            null,
            null,
            "",
            userID,
            userRole,
            pints.text.toString().substring(0, 1),
            null,
            message.text.toString(),
            null,
            dbDate,
            patientName.text.toString(),
            venueID,
            latitude,
            longitude,
            venueContact,
            venueName,
            workStart,
            workEnd
        )

        requestViewModel.postDonation(postDonation)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}