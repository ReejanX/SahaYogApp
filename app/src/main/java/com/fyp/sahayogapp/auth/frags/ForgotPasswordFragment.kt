package com.fyp.sahayogapp.auth.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav

import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.auth.viewModel.ForgotPasswordViewModel
import com.fyp.sahayogapp.dashboard.model.APIResponse

private const val EMAIL = "EMAIL"


class ForgotPasswordFragment : Fragment() {
    private lateinit var continueBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var emailED: EditText
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        continueBtn = view.findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            Toast.makeText(requireContext(),"To Be Implemented",Toast.LENGTH_LONG).show()
        }
        backBtn = view.findViewById(R.id.backBtn)
        emailED = view.findViewById(R.id.email)
        backBtn.setOnClickListener{
            nav(view,R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        continueBtn=view.findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            sendOTP()
            sendOTPObservable()
        }
    }

    private fun sendOTP() {
        val email = ResetPassword(emailED.text.toString(),"","")
        forgotPasswordViewModel.sendOTP(email)
    }
    private fun sendOTPObservable() {
        forgotPasswordViewModel.sendOTPObservable().observe(requireActivity(), Observer <APIResponse?>{
            if (it == null){
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
            }
            if (it.code=="200"){
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navigateToOTP()
            }else{

                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToOTP() {

        var bundle = Bundle()
        bundle.putString(EMAIL, emailED.text.toString())
        Navigation.findNavController(continueBtn)
            .navigate(R.id.action_forgotPasswordFragment_to_OTPFragment, bundle)

    }

}