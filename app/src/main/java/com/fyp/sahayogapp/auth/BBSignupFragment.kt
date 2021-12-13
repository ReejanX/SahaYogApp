package com.fyp.sahayogapp.auth

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.google.android.material.textfield.TextInputLayout

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