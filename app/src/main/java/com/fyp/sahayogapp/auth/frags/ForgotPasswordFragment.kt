package com.fyp.sahayogapp.auth.frags

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.auth.viewModel.ForgotPasswordViewModel
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.model.APIResponse

private const val EMAIL = "EMAIL"

private const val TAG = "ForgotPasswordFragment"
class ForgotPasswordFragment : BaseFragment() {
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
        continueBtn = view.findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            Toast.makeText(requireContext(), "To Be Implemented", Toast.LENGTH_LONG).show()
        }
        backBtn = view.findViewById(R.id.backBtn)
        emailED = view.findViewById(R.id.email)
        backBtn.setOnClickListener {
            nav(view, R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        continueBtn = view.findViewById(R.id.continueBtn)
        forgotPasswordViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        sendOTPObservable()
        continueBtn.setOnClickListener {
            sendOTP()
        }
    }

    private fun sendOTP() {
        showProgress()
        val email = ResetPassword(emailED.text.toString(),"","")
        forgotPasswordViewModel.sendOTP(email)
    }
    private fun sendOTPObservable() {

        forgotPasswordViewModel.sendOTPObservable().observe(requireActivity(), Observer <APIResponse?> {


            Log.i(TAG, "sendOTPObservable: ${Looper.getMainLooper() == Looper.myLooper()}")
            if (it == null) {
                dismissProgress()
                showAlert("Sorry!","Server Request Failed!")
            }
            if (it.code == "200") {
                dismissProgress()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                navigateToOTP()
            } else {
                dismissProgress()
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