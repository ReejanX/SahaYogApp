package com.fyp.sahayogapp.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.REMEMBER_KEY
import com.fyp.sahayogapp.auth.AuthActivity.Companion.REMEMBER_PREF
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.dashboard.DashActivity
import com.fyp.sahayogapp.permissions.PermissionLocation
import java.util.*


class LoginFragment : Fragment() {
    private lateinit var root: LinearLayout
    private lateinit var scroll: LinearLayout
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var checkBox: CheckBox
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var forgot: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false)


    }

//    private fun nav(view: View, id: Int) {
//        Navigation.findNavController(view).navigate(id)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        PermissionLocation.checkLocationAccess(requireActivity(),requireContext())
        root.setOnClickListener {
            it.hideKeyboard(requireContext())
        }
        scroll.setOnClickListener {
            it.hideKeyboard(requireContext())
        }
        signUpBtn.setOnClickListener {
            nav(signUpBtn, R.id.action_loginFragment_to_userTypeFragment)
        }
        loginBtn.setOnClickListener {
            validate()
        }
        if (getFromPref(REMEMBER_KEY, REMEMBER_PREF) == true){
            startActivity(Intent(requireContext(), DashActivity::class.java))
        }
        forgot.setOnClickListener {
            nav(view,R.id.action_loginFragment_to_forgotPasswordFragment)
        }
//        checkBox.setOnCheckedChangeListener { compoundButton, b ->
//            if (compoundButton.isChecked) {
//                rememberChecked()
//
//            } else if (!compoundButton.isChecked) {
//                rememberUnChecked()
//            }
//
//        }

    }
    //updating sharedPreferences when unchecked keep me logged in
    private fun rememberUnChecked() {
        val sharedPreferences =
            requireContext().getSharedPreferences(REMEMBER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(REMEMBER_KEY, false)
        editor.apply()
    }
    //updating sharedPreferences when checked keep me logged in
    private fun rememberChecked() {
        val sharedPreferences =
            requireContext().getSharedPreferences(REMEMBER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(REMEMBER_KEY, true)
        editor.apply()
    }
    //reading sharedPreferences File
    private fun getFromPref( key: String?, pref: String?): Boolean? {
        val myPrefs = requireContext().getSharedPreferences(pref, Context.MODE_PRIVATE)
        return myPrefs.getBoolean(key, false)
    }
    //initializing components of the view
    private fun initView(view: View) {
        root = view.findViewById(R.id.root)
        scroll = view.findViewById(R.id.scroll)
        signUpBtn = view.findViewById(R.id.signUpBtn)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        checkBox = view.findViewById(R.id.checkbox)
        loginBtn = view.findViewById(R.id.login)
        forgot = view.findViewById(R.id.forgotBtn)
    }

//    fun View.hideKeyboard() {
//        val inputMethodManager =
//            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
//    }

    private fun validate() {
        PermissionLocation.checkLocationAccess(requireActivity(),requireContext())
        if (email.text.isNullOrEmpty()) {
            email.error = "Enter Email Address"
            return
        }

        if (password.text.isNullOrEmpty()) {
            password.error = "Enter Password"
            return
        }
        if (email.text.toString().lowercase(Locale.getDefault())
            == "reejan" || password.text.toString() == "reejan"
        ) {
            if (checkBox.isChecked()){
            rememberChecked()
        }

            Toast.makeText(requireContext(), "Welcome Boss", Toast.LENGTH_LONG).show()
            startActivity(Intent(requireContext(), DashActivity::class.java))

        }else{
        Toast.makeText(requireContext(), "You not the Boss", Toast.LENGTH_LONG).show()
            return
        }




    }
}

