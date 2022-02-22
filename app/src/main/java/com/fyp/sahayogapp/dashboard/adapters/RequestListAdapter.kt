package com.fyp.sahayogapp.dashboard.adapters

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fyp.sahayogapp.auth.*
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.utils.Conts.DONATION_DATA
import com.fyp.sahayogapp.utils.DateFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.dashboard.DashActivity
import com.fyp.sahayogapp.dashboard.HomeFragment


class RequestListAdapter: RecyclerView.Adapter<RequestListAdapter.MyViewHolder>() {
    private lateinit var mlistener: onDonateClick
    var userList = mutableListOf<DonationRequestModel>()

    fun setOnDonateClick(listener: onDonateClick){
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.order_card_layout,parent,false)
        return MyViewHolder(inflater,mlistener)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder ,position: Int) {
        viewHolder.bindView(userList[position])

        }


    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(view: View,listener:onDonateClick) : RecyclerView.ViewHolder(view) {
        val bloodType: CustomTextView = view.findViewById(R.id.bloodType)
        val requestType: CustomTextView = view.findViewById(R.id.requestTypeTV)
        val patientName: CustomTextView = view.findViewById(R.id.patientNameTV)
        val unitsRequired: CustomTextView = view.findViewById(R.id.required_unitTV)
        val requiredDate: CustomTextView = view.findViewById(R.id.dateTillTV)
        val donationStatus: CustomTextView = view.findViewById(R.id.requestStatusTV)
        val venue_name: TextView = view.findViewById(R.id.venue_name)
        val donateBtn: TextView = view.findViewById(R.id.donateBtn)
        val remaining :TextView = view.findViewById(R.id.remainingUnitTV)
        init {
            donateBtn.setOnClickListener{
                listener.goToDetails(adapterPosition)
            }
}
        fun bindView(data: DonationRequestModel) {
            bloodType.text = data.blood_group
            if (data?.donation_status =="false"){
                donationStatus.text = "In Progress"
            }else{
                donationStatus.text = "Completed"
            }
            patientName.text = data.patient_name
            unitsRequired.text = data.required_amount
            requestType.text = data.donation_type
            requiredDate.text = DateFormatter.convertDate(data.date_till)
            venue_name.text = data.venue_name
            remaining.text =  "( Remaining ${data.remaining_unit} )"
        }
    }

    interface onDonateClick{
        fun  goToDetails(position:Int)
    }

}
