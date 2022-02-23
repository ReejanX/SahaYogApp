package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.dashboard.model.ChangePassword
import com.fyp.sahayogapp.dashboard.model.DonorInfoResponse
import com.fyp.sahayogapp.dashboard.viewModel.ProfileViewModel
import com.fyp.sahayogapp.utils.PreferenceHelper
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import com.fyp.sahayogapp.utils.PreferenceHelper.init

class ProfileFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var last = ""
    private lateinit var changePassword:TextView
    private lateinit var bloodGroup : TextView
    private lateinit var lastdonated : TextView
    private lateinit var phone : TextView
    private lateinit var sex : TextView
    private lateinit var email : TextView
    private lateinit var name : TextView
    private lateinit var userCard :CardView
    private lateinit var profileViewModel:ProfileViewModel
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
        getDonorDetails()
        getDonorDetailsObservable()
    }

    private fun initView(view: View) {
        changePassword = view.findViewById(R.id.changePassword)
        userCard = view.findViewById(R.id.card2)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        lastdonated = view.findViewById(R.id.lastBleed)
        phone = view.findViewById(R.id.phoneNumber)
        sex = view.findViewById(R.id.sex)
        name = view.findViewById(R.id.userNameTv)
        email = view.findViewById(R.id.userEmailTv)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

    }

    private fun getDonorDetails() {
        init(requireContext())
        val user_id = getUserId()
        val token = PreferenceHelper.getAccessToken()
        profileViewModel.getDonorDetails(token!!,user_id!!)
    }

    private fun getDonorDetailsObservable() {
        profileViewModel.donorDetailObservable().observe(requireActivity(), Observer <DonorInfoResponse?>{
            if (it == null){
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                userCard.visibility = View.GONE
            }
            if (it.code=="200"){

//                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                bloodGroup.text=it.data.blood_group
                sex.text=it.data.sex
                phone.text=it.data.phone
                email.text=it.data.email
                name.text = it.data.name
                if (it.data.last_donated==null) {
                last = "Unknown"
                }else{
                    last = "Change Date"
                }

                lastdonated.text=last
            }else{
                userCard.visibility = View.GONE

                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


}