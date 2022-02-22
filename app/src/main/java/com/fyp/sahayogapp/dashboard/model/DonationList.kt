package com.fyp.sahayogapp.dashboard.model

import java.io.Serializable

data class DonationRequestModel(
    val donation_id: String,
    val blood_group: String,
    val donation_type: String,
    val donation_status: String,
    val user_name : String,
    val user_phone : String,
    val user_id: String,
    val required_amount: String,
    val remaining_unit: String,
    val message: String,
    val date_till: String,
    val patient_name: String,
    val latitude : String,
    val longitude  : String,
    val venue_contact : String,
    val venue_name : String,
    val open_time : String,
    val close_time : String,

) : Serializable