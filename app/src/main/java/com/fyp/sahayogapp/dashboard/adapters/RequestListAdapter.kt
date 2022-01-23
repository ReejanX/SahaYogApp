package com.fyp.sahayogapp.dashboard.adapters

import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.utils.DateFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class RequestListAdapter: RecyclerView.Adapter<RequestListAdapter.MyViewHolder>() {

    var userList = mutableListOf<DonationRequestModel>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.order_card_layout,parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bindView(userList[position])

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bloodType: CustomTextView = view.findViewById(R.id.bloodType)
        val requestType: CustomTextView = view.findViewById(R.id.requestTypeTV)
        val patientName: CustomTextView = view.findViewById(R.id.patientNameTV)
        val unitsRequired: CustomTextView = view.findViewById(R.id.required_unitTV)
        val requiredDate: CustomTextView = view.findViewById(R.id.dateTillTV)
        val donationStatus: CustomTextView = view.findViewById(R.id.requestStatusTV)
        val venue_name: TextView = view.findViewById(R.id.venue_name)

        fun bindView(data:DonationRequestModel){
            bloodType.text = data.blood_group
            donationStatus.text = data.donation_status
            patientName.text= data.user_id
            unitsRequired.text = data.required_amount
            requestType.text = data.donation_type
            requiredDate.text = DateFormatter.convertDate(data.date_till)
            venue_name.text = data.venue_name}
    }
}
