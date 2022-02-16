package com.fyp.sahayogapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.chaos.view.PinView
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.auth.viewModel.ForgotPasswordViewModel
import com.fyp.sahayogapp.dashboard.model.APIResponse


/**
 * A simple [Fragment] subclass.
 * Use the [OTPFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val EMAIL = "EMAIL"
class OTPFragment : Fragment() {

    private lateinit var pinView: PinView
    private lateinit var continueBtn : Button
    private lateinit var backBtn : ImageButton
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    private var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        email = it.getString(EMAIL,"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_t_p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        pinView = view.findViewById(R.id.pinView)
        continueBtn = view.findViewById(R.id.continueBtn)
        pinView.isFocused
        backBtn=view.findViewById(R.id.backBtn)

        backBtn.setOnClickListener{
            nav(view,R.id.action_OTPFragment_to_forgotPasswordFragment)
        }
        continueBtn.setOnClickListener {
            if (pinView.text.toString().length<6) {

                Toast.makeText(requireContext(), "Enter 6 digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            checkOTP()
            checkOTPObservable()
            Toast.makeText(requireContext(), pinView.text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkOTP() {
        var resetPassword = ResetPassword(email,pinView.text.toString(),"")
        forgotPasswordViewModel.checkOTP(resetPassword)
    }
    private fun checkOTPObservable(){
        forgotPasswordViewModel.otpCheckObservable().observe(requireActivity(), Observer <APIResponse?>{
            if (it==null){

                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()

            }
            if(it.code == "200"){
                Toast.makeText(requireContext(), "OTP Verified", Toast.LENGTH_SHORT).show()
                navigateToResetPassword()
            }
            else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToResetPassword() {
        var bundle = Bundle()
        bundle.putString(EMAIL,email)
        bundle.putString("RESETKEY","001")
        Navigation.findNavController(continueBtn)
            .navigate(R.id.action_OTPFragment_to_passwordFragment, bundle)
    }


}