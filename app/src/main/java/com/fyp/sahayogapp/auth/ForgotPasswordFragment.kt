package com.fyp.sahayogapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav


class ForgotPasswordFragment : Fragment() {
    private lateinit var continueBtn: Button
    private lateinit var backBtn: ImageButton

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
            Toast.makeText(requireContext(),"To Be Implemented",Toast.LENGTH_LONG).show()
        }
        backBtn = view.findViewById(R.id.backBtn)

        backBtn.setOnClickListener{
            nav(view,R.id.action_forgotPasswordFragment_to_loginFragment)
        }
    }

}