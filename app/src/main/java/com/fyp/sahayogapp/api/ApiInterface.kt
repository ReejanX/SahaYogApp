package com.fyp.sahayogapp.api

import com.fyp.sahayogapp.auth.model.RegisterUser
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.dashboard.model.*
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET(API_URL.GET_DONATION_LIST)
    fun getAllRequestList(): Call<List<DonationRequestModel>>

    @POST(API_URL.USER_LOGIN)
    fun loginUser(@Body params: UserLogin): Call<LoginResponse>

    @POST(API_URL.USER_REGISTER)
    fun registerUser(@Body params: RegisterUser): Call<APIResponse>

    @POST(API_URL.SEND_OTP)
    fun sendOTP(@Body params: ResetPassword): Call<APIResponse>

    @POST(API_URL.OTP_VERIFY)
    fun checkOTP(@Body params: ResetPassword) :Call<APIResponse>

    @PUT(API_URL.RESET_PASSWORD)
    fun resetPassword(@Body params: ResetPassword): Call<APIResponse>

    @PUT(API_URL.CHANGE_PASSWORD)
    fun changePassword(
        @Header("token")  basicToken: String,
        @Body params: ChangePassword): Call<APIResponse>

    @GET(API_URL.DONOR_DETAILS)
    fun getDonorDetails(
        @Header("token")  basicToken: String,
        @Query("id") userID: String): Call<DonorInfoResponse>

}
