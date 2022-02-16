package com.fyp.sahayogapp.dashboard.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.dashboard.model.UserLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginUserViewModel"
class LoginUserViewModel: ViewModel() {

    lateinit var loginUser : MutableLiveData<APIResponse?>

    init {
        loginUser= MutableLiveData()
    }

    fun loginUserObservable() : MutableLiveData<APIResponse?> {

        return  loginUser
    }

    fun loginUser(userLogin: UserLogin){

        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.loginUser(userLogin)
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {

                if (response.isSuccessful){

                    loginUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                loginUser.postValue(null)
            }

        })
    }
    }
