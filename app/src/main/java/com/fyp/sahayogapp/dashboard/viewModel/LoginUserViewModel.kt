package com.fyp.sahayogapp.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.LoginResponse
import com.fyp.sahayogapp.dashboard.model.UserLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "LoginUserViewModel"
class LoginUserViewModel: ViewModel() {

    lateinit var loginUserLD: MutableLiveData<LoginResponse>

    init {
        loginUserLD = MutableLiveData()
    }

    fun loginUserObservable(): MutableLiveData<LoginResponse> {

        return loginUserLD
    }

    fun loginUser(userLogin: UserLogin){
        viewModelScope.launch(Dispatchers.IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val response = retrofitInstance.loginUser(userLogin)
            loginUserLD.postValue(response)
        }
    }
    }

