package com.fyp.sahayogapp.auth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.auth.model.ResetPassword
import com.fyp.sahayogapp.dashboard.model.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ForgotPasswordViewModel"

class ForgotPasswordViewModel : ViewModel() {

    var sendOTP: MutableLiveData<APIResponse?> = MutableLiveData()

    fun sendOTPObservable(): LiveData<APIResponse?> {

        return sendOTP
    }

    fun sendOTP(email: ResetPassword) {
        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.sendOTP(email)
            sendOTP.postValue(call)
        }
    }

    var resetPasswordResponse: MutableLiveData<APIResponse?> = MutableLiveData()

    fun resetPasswordObservable(): MutableLiveData<APIResponse?> {

        return resetPasswordResponse
    }

    fun resetPassword(resetPassword: ResetPassword) {


        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.resetPassword(resetPassword)
            resetPasswordResponse.postValue(call)
        }
    }

    var otpCheckResponse: MutableLiveData<APIResponse?> = MutableLiveData()
    fun otpCheckObservable(): MutableLiveData<APIResponse?> {
        return otpCheckResponse
    }

    fun checkOTP(email: ResetPassword) {
        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.checkOTP(email)
            otpCheckResponse.postValue(call)
        }


    }

}