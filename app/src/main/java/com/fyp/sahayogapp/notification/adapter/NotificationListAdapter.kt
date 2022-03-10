package com.fyp.sahayogapp.notification.adapter

import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.model.NotificationData
import com.fyp.sahayogapp.notification.userId
import com.fyp.sahayogapp.utils.DateFormatter

class NotificationListAdapter : RecyclerView.Adapter<NotificationListAdapter.MyViewHolder>() {
    var notificationList = mutableListOf<NotificationData>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title : CustomTextView = view.findViewById(R.id.title)
        val body : CustomTextView = view.findViewById(R.id.body)
        val date : TextView = view.findViewById(R.id.date)

        fun bindView(data: NotificationData){
            title.text= data.title
            body.text= data.body
            date.text =  substring(data.time,0,10)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_layout, parent, false)
        return NotificationListAdapter.MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(notificationList[position])
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}