package com.fyp.sahayogapp.auth.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.permissions.PermissionAlerts
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.google.android.material.card.MaterialCardView


class UserTypeFragment : Fragment() {
    private lateinit var donorCard: MaterialCardView
    private lateinit var bBankCard: MaterialCardView
    private lateinit var backBtn: ImageButton
    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        donorCard.setOnClickListener {
            donorCard.isChecked = true
            bBankCard.isChecked = false
        }

        bBankCard.setOnClickListener {
            bBankCard.isChecked = true
            donorCard.isChecked = false
        }
        backBtn.setOnClickListener {
            nav(backBtn, R.id.action_userTypeFragment_to_loginFragment)
        }

        signupBtn.setOnClickListener {
            navigate()
        }

    }

    private fun nav(view: View, id: Int) {
        Navigation.findNavController(view).navigate(id)
    }

    private fun navigate() {
        when {
            donorCard.isChecked -> {
                nav(signupBtn, R.id.action_userTypeFragment_to_signUpFragment)
            }
            bBankCard.isChecked -> {
                nav(signupBtn, R.id.action_userTypeFragment_to_BBSignupFragment)
            }
            else -> {
                Toast.makeText(requireContext(), "Please select User Type", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun initView(view: View) {
        donorCard = view.findViewById(R.id.donorCard)
        bBankCard = view.findViewById(R.id.bBankCard)
        backBtn = view.findViewById(R.id.backBtn)
        signupBtn = view.findViewById(R.id.signUp)
    }

}