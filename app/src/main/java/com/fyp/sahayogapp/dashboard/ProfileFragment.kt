package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R

class ProfileFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var changePassword:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        changePassword.setOnClickListener {
            Navigation.findNavController(changePassword).navigate(R.id.action_nav_profile_to_changePasswordFragment)
        }
    }

    private fun initView(view: View) {
        changePassword = view.findViewById(R.id.changePassword)

    }

}