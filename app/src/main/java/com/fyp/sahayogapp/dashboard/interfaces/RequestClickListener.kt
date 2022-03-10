package com.fyp.sahayogapp.dashboard.interfaces

import com.fyp.sahayogapp.dashboard.model.DonationRequestModel

interface RequestClickListener {
    fun onItemClick(donationRequestModel: DonationRequestModel?, position: Int)
}