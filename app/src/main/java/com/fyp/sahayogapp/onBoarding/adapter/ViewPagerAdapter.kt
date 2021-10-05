package com.fyp.sahayogapp.onBoarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fyp.sahayogapp.R

class ViewPagerAdapter(
    private var title: List<String>,
    private var details: List<String>,
    private var images: List<Int>,
) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.on_boarding_layout, parent, false)
        )

    }

    class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title);
        val itemDetail: TextView = itemView.findViewById(R.id.details);
        val itemImage: ImageView = itemView.findViewById(R.id.sliderImage);

    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDetail.text = details[position]
        holder.itemImage.setBackgroundResource(images[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

}