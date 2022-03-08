package com.fyp.sahayogapp.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.model.DonorInfoResponse
import com.fyp.sahayogapp.dashboard.model.HospitalInfoResponse
import com.fyp.sahayogapp.dashboard.viewModel.ProfileViewModel
import com.fyp.sahayogapp.utils.Conts.DONOR
import com.fyp.sahayogapp.utils.Conts.HOSPITAL
import com.fyp.sahayogapp.utils.PreferenceHelper.clearPref
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole

class ProfileFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null
    var last = ""
    private lateinit var changePassword:TextView
    private lateinit var logout:TextView
    private lateinit var bloodGroup : TextView
    private lateinit var lastdonated : TextView
    private lateinit var phone : TextView
    private lateinit var sex : TextView
    private lateinit var email : TextView
    private lateinit var name : TextView
    private lateinit var contact : TextView
    private lateinit var startTime : TextView
    private lateinit var closeTime : TextView
    private lateinit var address : TextView
    private lateinit var userCard :CardView
    private lateinit var donorLayout : LinearLayout
    private lateinit var hospitalLayout : LinearLayout
    private lateinit var profileViewModel:ProfileViewModel

    val role = getUserRole()
    val user_id = getUserId()
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
            Navigation.findNavController(changePassword)
                .navigate(R.id.action_nav_profile_to_changePasswordFragment)
        }
        logout.setOnClickListener {
            showAlert("Logout",
                "Are you sure you want to logout?",
                "Logout",
                DialogInterface.OnClickListener { dialog, which ->

                    clearPref()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    Toast.makeText(requireContext(), "Implement Logout", Toast.LENGTH_SHORT).show()
                })
        }
        getDonorDetailsObservable()
        getHospitalDetailObservable()


        if (role == DONOR) {
            getDonorDetails()
        }else if (role == HOSPITAL){

            getHospitalDetails()

        }
    }

    private fun initView(view: View) {
        changePassword = view.findViewById(R.id.changePassword)
        logout = view.findViewById(R.id.logout)
        userCard = view.findViewById(R.id.card2)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        lastdonated = view.findViewById(R.id.lastBleed)
        phone = view.findViewById(R.id.phoneNumber)
        sex = view.findViewById(R.id.sex)
        name = view.findViewById(R.id.userNameTv)
        email = view.findViewById(R.id.userEmailTv)
        donorLayout = view.findViewById(R.id.donorInfo)
        hospitalLayout = view.findViewById(R.id.hospitalInfo)
        contact = view.findViewById(R.id.hContact)
        startTime = view.findViewById(R.id.openTime)
        closeTime = view.findViewById(R.id.closeTime)
        address = view.findViewById(R.id.addressH)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

    }

    private fun getDonorDetails() {
        profileViewModel.getDonorDetails(user_id!!)
        showProgress()
    }
    private fun getHospitalDetails() {
        profileViewModel.getHospitalDetails(user_id!!)
        showProgress()
    }


    private fun getDonorDetailsObservable() {
        profileViewModel.donorDetailObservable().observe(requireActivity(), Observer <DonorInfoResponse?>{
            if (it == null){
                dismissProgress()
                showAlert("Sorry!","Server Request Failed")
                userCard.visibility = View.GONE
            }
            if (it.code=="200"){
                dismissProgress()
                donorLayout.visibility = View.VISIBLE
//                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                bloodGroup.text=it.data.blood_group
                sex.text=it.data.sex
                phone.text=it.data.phone
                email.text=it.data.email
                name.text = it.data.name
                if (it.data.last_donated==null) {
                last = "Unknown"
                }else{
                    last = it.data.last_donated.substring(0,10)
                }

                lastdonated.text=last
            }else{
                userCard.visibility = View.GONE
                dismissProgress()
                showAlert("Sorry!",it.message)
            }
        })

    }

    private fun getHospitalDetailObservable(){
        profileViewModel.hospitalDetailObservable().observe(requireActivity(),Observer <HospitalInfoResponse?>{
            if (it == null ){
                dismissProgress()
                showAlert("Sorry!","Server Request Failed")
                userCard.visibility = View.GONE

            }
            if (it.code=="200"){
                dismissProgress()
                hospitalLayout.visibility = View.VISIBLE

                email.text=it.data.email
                name.text = it.data.name
                contact.text = it.data.phone
                startTime.text = it.data.open_time
                closeTime.text = it.data.close_time
                address.text = it.data.latitude +","+ it.data.longitude







            }
            else{
                userCard.visibility = View.GONE
                dismissProgress()
                showAlert("Sorry!!",it.message)
            }
        })
    }


}