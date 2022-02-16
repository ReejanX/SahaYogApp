package com.fyp.sahayogapp.auth.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.dashboard.model.APIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ForgotPasswordViewModel"

class ForgotPasswordViewModel : ViewModel() {

    var sendOTP: MutableLiveData<APIResponse?> = MutableLiveData()

    fun sendOTPObservable(): MutableLiveData<APIResponse?> {

        return sendOTP
    }

    fun sendOTP(email: ResetPassword) {

        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.sendOTP(email)
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {

                if (response.isSuccessful) {

                    sendOTP.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                sendOTP.postValue(null)
            }

        })
    }

    var resetPasswordResponse: MutableLiveData<APIResponse?> = MutableLiveData()

    fun resetPasswordObservable(): MutableLiveData<APIResponse?> {

        return resetPasswordResponse
    }

    fun resetPassword(resetPassword: ResetPassword) {

        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.resetPassword(resetPassword)
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {

                if (response.isSuccessful) {
                    resetPasswordResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resetPasswordResponse.postValue(null)
            }

        })
    }

    var otpCheckResponse :MutableLiveData<APIResponse?> = MutableLiveData()
    fun otpCheckObservable() : MutableLiveData<APIResponse?> {
        return otpCheckResponse
    }

    fun checkOTP(email: ResetPassword){
        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.checkOTP(email)
        call.enqueue(object :Callback<APIResponse>{
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
               if (response.isSuccessful){

                   otpCheckResponse.postValue(response.body())
               }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                otpCheckResponse.postValue(null)
            }
        })
    }

}