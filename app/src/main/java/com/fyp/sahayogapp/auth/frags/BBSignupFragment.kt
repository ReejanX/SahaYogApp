package com.fyp.sahayogapp.auth.frags

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.google.android.material.textfield.TextInputLayout
private const val NAME = "NAME"
private const val EMAIL = "EMAIL"
private const val BLOODGROUP = "BLOODGROUP"
private const val GENDER = "GENDER"
private const val PHONENUMBER = "PHONENUMBER"
private const val USERTYPE = "USERTYPE"
private const val REGISTRATION = "REGISTRATION"
private const val LANDLINE = "LANDLINE"
private const val WORKDAY = "WORKDAY"
private const val OPENTIME = "OPENTIME"
private const val CLOSETIME = "CLOSETIME"
private const val LATITUDE = "LATITUDE"
private const val LONGITUDE = "LONGITUDE"

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
    private lateinit var back : ImageButton
    lateinit var timefilter :InputFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        loginBtn.setOnClickListener {
            nav(view, R.id.action_BBSignupFragment_to_loginFragment)
        }
        back.setOnClickListener{
            nav(view,R.id.action_BBSignupFragment_to_userTypeFragment)
        }
       location.setOnClickListener {
           location.setText("Hello there")
       }
        signUpBtn.setOnClickListener {
            nav(view,R.id.action_BBSignupFragment_to_passwordFragment)
        }

    }


    private fun navigateToPassword() {

        var bundle = Bundle()
        bundle.putString(NAME, name.text.toString())
        bundle.putString(EMAIL, email.text.toString())
        bundle.putString(PHONENUMBER, phoneNumber.text.toString())
        bundle.putString(USERTYPE, "donor")

        Navigation.findNavController(loginBtn)
            .navigate(R.id.action_signUpFragment_to_passwordFragment, bundle)

    }

    private fun initView(view: View) {
        root = view.findViewById(R.id.root)
        scroll = view.findViewById(R.id.scroll)
        signUpBtn = view.findViewById(R.id.signUp)
        loginBtn = view.findViewById(R.id.loginBtn)
        name = view.findViewById(R.id.hospital_name)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phoneNumber)
        location= view.findViewById(R.id.location)
        locationLayout = view.findViewById(R.id.locationLayout)
        back = view.findViewById(R.id.backBtn)

    }

}