package com.fyp.sahayogapp.api

import com.fyp.sahayogapp.auth.model.RegisterUser
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.dashboard.model.*
import retrofit2.http.*


interface ApiInterface {
    @GET(API_URL.GET_DONATION_LIST)
    suspend fun getAllRequestList(): List<DonationRequestModel>

    @POST(API_URL.USER_LOGIN)
    suspend fun loginUser(@Body params: UserLogin): LoginResponse

    @POST(API_URL.USER_REGISTER)
    suspend fun registerUser(@Body params: RegisterUser): APIResponse

    @POST(API_URL.SEND_OTP)
    suspend fun sendOTP(@Body params: ResetPassword): APIResponse

    @POST(API_URL.OTP_VERIFY)
    suspend fun checkOTP(@Body params: ResetPassword) :APIResponse

    @PUT(API_URL.RESET_PASSWORD)
    suspend fun resetPassword(@Body params: ResetPassword): APIResponse

    @PUT(API_URL.CHANGE_PASSWORD)
    suspend fun changePassword(@Body params: ChangePassword): APIResponse

    @GET(API_URL.DONOR_DETAILS)
    suspend fun getDonorDetails(@Query("id") userID: String): DonorInfoResponse

    @GET(API_URL.HOSPITAL_DETAILS)
    suspend fun getHospitalDetails(@Query("id") userID: String): HospitalInfoResponse

    @GET(API_URL.GET_VENUE)
    suspend fun getVenues(): List<VenueData>

    @POST(API_URL.POST_REQUEST)
    suspend fun postDonationRequest(@Body params: DonationRequestModel): APIResponse

    @POST(API_URL.ACCEPT_REQUEST)
    suspend fun acceptDonationRequest(@Body params:AcceptDonation): APIResponse

}
