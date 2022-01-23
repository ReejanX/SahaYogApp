package com.fyp.sahayogapp.dashboard

import com.fyp.sahayogapp.dashboard.model.DonationRequestModel

interface RequestClickListener {
    fun onItemClick(donationRequestModel: DonationRequestModel?, position: Int)
}