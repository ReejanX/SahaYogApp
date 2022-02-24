package com.fyp.sahayogapp.auth.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.auth.model.RegisterUser
import com.fyp.sahayogapp.dashboard.model.APIResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RegisterUserViewModel"
class RegisterUserViewModel : ViewModel(){


        lateinit var registerUser : MutableLiveData<APIResponse?>

        init {
            registerUser= MutableLiveData()
        }

        fun registerUserObservable() : MutableLiveData<APIResponse?> {

            return  registerUser
        }

        fun registerUser(userRegister: RegisterUser){
            viewModelScope.launch(IO) {
                val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
                val call = retrofitInstance.registerUser(userRegister)
                registerUser.postValue(call)
            }
            }

}