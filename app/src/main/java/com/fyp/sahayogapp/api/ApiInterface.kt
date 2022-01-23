package com.fyp.sahayogapp.api

import com.fyp.sahayogapp.api.API_URL.GET_DONATION_LIST
import retrofit2.http.GET
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import retrofit2.Call


interface ApiInterface {
    @GET(GET_DONATION_LIST)
    fun getAllRequestList(): Call<List<DonationRequestModel>>


}
