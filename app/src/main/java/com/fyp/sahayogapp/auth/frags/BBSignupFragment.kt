package com.fyp.sahayogapp.auth.frags

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.utils.Conts

import com.google.android.material.textfield.TextInputLayout
import java.util.*


class BBSignupFragment : Fragment() {
    private lateinit var root: LinearLayout
    private lateinit var scroll: LinearLayout
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var location: EditText
    private lateinit var locationLayout: TextInputLayout
    private lateinit var back: ImageButton
    private lateinit var startTime: EditText
    private lateinit var endTime: EditText
    private lateinit var getLocation: TextView
    lateinit var timefilter: InputFilter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bb_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        root.setOnClickListener {
            it.hideKeyboard(requireContext())
        }
        scroll.setOnClickListener {
            it.hideKeyboard(requireContext())
        }

        val startTimePicker: TimePickerDialog
        val endTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        startTimePicker = TimePickerDialog(requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    startTime.setText(String.format("%d : %d", hourOfDay, minute))
                }
            },
            hour,
            minute,
            false)

        endTimePicker = TimePickerDialog(requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    endTime.setText(String.format("%d : %d", hourOfDay, minute))
                }
            },
            hour,
            minute,
            false)


        loginBtn.setOnClickListener {
            nav(view, R.id.action_BBSignupFragment_to_loginFragment)


        }
        back.setOnClickListener {
            nav(view, R.id.action_BBSignupFragment_to_userTypeFragment)
        }
        location.setOnClickListener {
            location.setText("Hello there")
        }
        signUpBtn.setOnClickListener {
           validate()
        }
        startTime.setOnClickListener {
            startTimePicker.setTitle("Select Work Start Time")
            startTimePicker.show()
        }
        endTime.setOnClickListener {
            endTimePicker.setTitle("Select Work End Time")
            endTimePicker.show()
        }


    }

    private fun validate() {
        if (email.text.isNullOrEmpty()) {
            email.error = "Enter Email Address"
            return
        }
        if (name.text.isNullOrEmpty()) {
            name.error = "Enter Hospital Name"
            return
        }
        if (phoneNumber.text.isNullOrEmpty()) {
            email.error = "Enter Phone Number"
            return
        }
        if (startTime.text.isNullOrEmpty()) {
            name.error = "Select Work Start Time"
            return
        }
        if (endTime.text.isNullOrEmpty()) {
            name.error = "Select Work End Time"
            return
        }
        navigateToPassword()
    }


    private fun navigateToPassword() {

        var bundle = Bundle()
        bundle.putString(Conts.NAME, name.text.toString())
        bundle.putString(Conts.EMAIL, email.text.toString())
        bundle.putString(Conts.PHONENUMBER, phoneNumber.text.toString())
        bundle.putString(Conts.USERTYPE, "hospital")
        bundle.putString(Conts.LATITUDE, "27.55555")
        bundle.putString(Conts.LONGITUDE, "85.98234234")
        bundle.putString(Conts.OPENTIME, startTime.text.toString())
        bundle.putString(Conts.CLOSETIME, endTime.text.toString())

        Navigation.findNavController(loginBtn)
            .navigate(R.id.action_BBSignupFragment_to_passwordFragment, bundle)

    }

    private fun initView(view: View) {
        root = view.findViewById(R.id.root)
        scroll = view.findViewById(R.id.scroll)
        signUpBtn = view.findViewById(R.id.signUp)
        loginBtn = view.findViewById(R.id.loginBtn)
        name = view.findViewById(R.id.hospital_name)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phoneNumber)
        location = view.findViewById(R.id.location)
        locationLayout = view.findViewById(R.id.locationLayout)
        back = view.findViewById(R.id.backBtn)
        startTime = view.findViewById(R.id.wtStart)
        endTime = view.findViewById(R.id.wtEnd)
        getLocation = view.findViewById(R.id.getLocation)
    }

}