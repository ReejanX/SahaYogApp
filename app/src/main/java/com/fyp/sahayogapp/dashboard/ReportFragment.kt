package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.dashboard.model.DonationRequestResponse
import com.fyp.sahayogapp.dashboard.viewModel.MyActivityViewModel
import com.fyp.sahayogapp.utils.Conts.DONOR
import com.fyp.sahayogapp.utils.Conts.HOSPITAL
import com.fyp.sahayogapp.utils.PreferenceHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var acceptCard : CardView
    private lateinit var requestsAccepted : TextView

    private lateinit var  myActivityViewModel: MyActivityViewModel

    val role = PreferenceHelper.getUserRole()
    val role_id = PreferenceHelper.getRoleID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myActivityViewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)
        getMyDoantionsAcceptedObserver()
        initView(view)

        if (role == HOSPITAL){

            acceptCard.visibility = View.GONE
        }
        if (role == DONOR ){

            getMyDoantionsAccepted(role_id)
        }
    }

    private fun getMyDoantionsAcceptedObserver() {
        myActivityViewModel.getAcceptedRequestObserver().observe(requireActivity(),Observer<DonationRequestResponse>{

            if (it==null){
                showAlert("Sorry","Server Request failed")
            }
            if (it.code=="200"){
                requestsAccepted.text = it.data.size.toString()
            }

        })
    }

    private fun getMyDoantionsAccepted(roleId: String?) {

        myActivityViewModel.getAcceptedRequest(roleId!!)

    }

    private fun initView(view: View) {
        acceptCard = view.findViewById(R.id.card2)
        requestsAccepted = view.findViewById(R.id.accepted)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}