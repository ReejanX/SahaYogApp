package com.fyp.sahayogapp.auth.frags

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.REMEMBER_KEY
import com.fyp.sahayogapp.auth.AuthActivity.Companion.REMEMBER_PREF
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.DashActivity
import com.fyp.sahayogapp.dashboard.model.LoginResponse
import com.fyp.sahayogapp.dashboard.model.UserLogin
import com.fyp.sahayogapp.dashboard.viewModel.LoginUserViewModel
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.fyp.sahayogapp.utils.Conts
import com.fyp.sahayogapp.utils.PreferenceHelper.initPref
import com.fyp.sahayogapp.utils.PreferenceHelper.saveAccessToken
import com.fyp.sahayogapp.utils.PreferenceHelper.saveUserId
import com.fyp.sahayogapp.utils.editor
import com.fyp.sahayogapp.utils.pref
import java.util.*
import com.google.firebase.iid.FirebaseInstanceId





class LoginFragment : BaseFragment() {
    private lateinit var root: LinearLayout
    private lateinit var scroll: LinearLayout
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var forgot: Button
    private lateinit var loginUserViewModel: LoginUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        loginUserViewModel= ViewModelProvider(this).get(LoginUserViewModel::class.java)
        loginUserObservable()

        signUpBtn.setOnClickListener {
            nav(signUpBtn, R.id.action_loginFragment_to_userTypeFragment)
        }
        loginBtn.setOnClickListener {

            validate()
//            startActivity(Intent(requireContext(), DashActivity::class.java))
        }
        if (getFromPref(REMEMBER_KEY, REMEMBER_PREF) == true){
            startActivity(Intent(requireContext(), DashActivity::class.java))
        }
        forgot.setOnClickListener {
            nav(view,R.id.action_loginFragment_to_forgotPasswordFragment)
        }






    }

    private fun loginUserObservable() {
        loginUserViewModel.loginUserObservable().observe(viewLifecycleOwner,Observer <LoginResponse>{
            if (it == null){
                dismissProgress()
                showAlert("Sorry!","Server Request Failed!")
                return@Observer
            }
            if (it.code=="200"){
                dismissProgress()
                rememberChecked()
                startActivity(Intent(requireContext(), DashActivity::class.java))
                initPref(requireActivity())
                saveAccessToken(it.data.token)
                saveUserId(it.data.user_id, it.data.user_role,it.data.type_id)
                return@Observer
//                Toast.makeText(context, it.data.user_name, Toast.LENGTH_SHORT).show()
            }else{
                dismissProgress()
                showAlert("Sorry!",it.message)

            }
        })
    }

    //updating sharedPreferences to keep the user logged in


    fun rememberChecked() {
        val sharedPreferences =
            requireContext().getSharedPreferences(REMEMBER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(REMEMBER_KEY, true)
        editor.apply()
    }
    //reading sharedPreferences File
   fun getFromPref( key: String?, pref: String?): Boolean? {
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
        loginBtn = view.findViewById(R.id.login)
        forgot = view.findViewById(R.id.forgotBtn)
    }

//    fun View.hideKeyboard() {
//        val inputMethodManager =
//            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
//    }

    private fun validate() {
        if (email.text.isNullOrEmpty()) {
            email.error = "Enter Email Address"
            return
        }
        if (password.text.isNullOrEmpty()) {
            password.error = "Enter Password"
            return
        }


        loginUser()

//        if (email.text.toString().lowercase(Locale.getDefault())
//            == "reejan" || password.text.toString() == "reejan"
//        ) {
//            if (checkBox.isChecked()){
//            rememberChecked()
//        }
//
//            Toast.makeText(requireContext(), "Welcome Boss", Toast.LENGTH_LONG).show()
//            startActivity(Intent(requireContext(), DashActivity::class.java))
//
//        }else{
//        Toast.makeText(requireContext(), "You not the Boss", Toast.LENGTH_LONG).show()
//            return
//        }




    }

    private fun loginUser() {
        showProgress()
        val userLogin = UserLogin(email.text.toString(),password.text.toString())

        loginUserViewModel.loginUser(userLogin)
    }
}


