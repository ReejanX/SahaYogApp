package com.fyp.sahayogapp.auth.frags

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.model.RegisterUser
import com.fyp.sahayogapp.auth.viewModel.RegisterUserViewModel
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.auth.viewModel.ForgotPasswordViewModel


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
private const val RESETKEY = "RESETKEY"

class PasswordFragment : Fragment() {

    private lateinit var etPassword : EditText
    private lateinit var etPassword2 : EditText
    private lateinit var signUp : Button
    private lateinit var frameOne : CardView
    private lateinit var frameTwo : CardView
    private lateinit var frameThree : CardView
    private lateinit var frameFour : CardView
    private lateinit var frameFive : CardView

    private lateinit var registerUserViewModel: RegisterUserViewModel
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    private var isAtLeast8 = false
    private  var hasUppercase:kotlin.Boolean = false
    private  var hasNumber:kotlin.Boolean = false
    private  var hasSymbol:kotlin.Boolean = false
    private  var matches:kotlin.Boolean = false
    private  var isRegistrationClickable:kotlin.Boolean = false

    private var name = ""
    private var email = ""
    private var gender = ""
    private var bloodGroup = ""
    private var phoneNumber = ""
    private var userType = ""
    private var password =""
    private var registration_number=""
    private var landline_number=""
    private var work_day=""
    private var open_time=""
    private var close_time=""
    private var latitude=""
    private var longitude=""
    private var resetKey=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            name = it.getString(NAME,"")
            email = it.getString(EMAIL,"")
            phoneNumber = it.getString(PHONENUMBER,"")
            gender = it.getString(GENDER,"")
            userType = it.getString(USERTYPE,"")
            bloodGroup = it.getString(BLOODGROUP,"")
            registration_number = it.getString(REGISTRATION,"")
            landline_number = it.getString(LANDLINE,"")
            work_day = it.getString(WORKDAY,"")
            open_time = it.getString(OPENTIME,"")
            close_time = it.getString(CLOSETIME,"")
            latitude = it.getString(LATITUDE,"")
            longitude = it.getString(LONGITUDE,"")
            resetKey = it.getString(RESETKEY,"")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        if (resetKey == "001") {
            signUp.text="Reset Password"
        }
        registerUserViewModel = ViewModelProvider(this).get(RegisterUserViewModel::class.java)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                passwordCheck()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        etPassword2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                passwordMatch()
            }

            override fun afterTextChanged(s: Editable) {}
        })


        signUp.setOnClickListener {
            if (isAtLeast8 && hasUppercase && hasNumber && hasSymbol && matches) {
                Toast.makeText(requireContext(), "valid", Toast.LENGTH_SHORT).show()
                password = etPassword.text.toString()

                if (resetKey == "001") {
//                    Toast.makeText(requireContext(), resetKey+"password reset", Toast.LENGTH_SHORT).show()
                    resetPassword()
                    resetPasswordObservable()
                } else {
                    registerUser()
                    registerUserObservable()
                }
            }
            else{

                return@setOnClickListener
            }
        }




    }


    private fun resetPassword() {
        val resetPassword = ResetPassword(email,"",password)
        forgotPasswordViewModel.resetPassword(resetPassword)
    }


    private fun resetPasswordObservable() {
         forgotPasswordViewModel.resetPasswordObservable().observe(requireActivity(), Observer <APIResponse?>{
            if (it == null){
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
            }
            if (it.code=="200"){

                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), AuthActivity::class.java))


            }else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }



    private fun registerUser() {
        val registerUser = RegisterUser(name,email,password,phoneNumber,userType,gender,bloodGroup,registration_number,landline_number,work_day,open_time,close_time,latitude,longitude)

        registerUserViewModel.registerUser(registerUser)
    }

    private fun registerUserObservable() {
        registerUserViewModel.registerUserObservable().observe(requireActivity(), Observer <APIResponse?>{
            if (it == null){
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
            }
            if (it.code=="200"){

                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), AuthActivity::class.java))


            }else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initView(view: View) {
        etPassword = view.findViewById(R.id.password)
        etPassword2 = view.findViewById(R.id.password2)
        signUp = view.findViewById(R.id.signUp)
        frameOne = view.findViewById(R.id.frameOne);
        frameTwo = view.findViewById(R.id.frameTwo);
        frameThree = view.findViewById(R.id.frameThree);
        frameFour = view.findViewById(R.id.frameFour);
        frameFive = view.findViewById(R.id.frameFive);
    }

    @SuppressLint("ResourceType")
    private fun passwordCheck(){
        val password = etPassword.text.toString()

        if (password.length >= 8) {
            isAtLeast8 = true;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            isAtLeast8 = false;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[A-Z].*)".toRegex())) {
            hasUppercase = true;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasUppercase = false;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[0-9].*)".toRegex())) {
            hasNumber = true;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasNumber = false;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[@\$#%^&*!+=_.-].*)".toRegex())) {
            hasSymbol = true;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasSymbol = false;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }

    }
    @SuppressLint("ResourceType")
    private fun passwordMatch(){
        val password = etPassword.text.toString()
        val password2 = etPassword2.text.toString()
        if (password==password2) {
            matches = true;
            frameFive.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            matches = false;
            frameFive.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
    }

}