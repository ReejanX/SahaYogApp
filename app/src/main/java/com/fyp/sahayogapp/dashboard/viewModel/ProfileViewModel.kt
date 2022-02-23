package com.fyp.sahayogapp.dashboard.viewModel



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginUserViewModel"
class ProfileViewModel: ViewModel() {

    lateinit var changePasswordLD : MutableLiveData<APIResponse?>
    lateinit var donorDetailsList : MutableLiveData<DonorInfoResponse?>

    init {
        changePasswordLD= MutableLiveData()
        donorDetailsList= MutableLiveData()
    }

    fun passwordChangeObservable() : MutableLiveData<APIResponse?> {

        return  changePasswordLD
    }
    fun donorDetailObservable() : MutableLiveData<DonorInfoResponse?> {

        return  donorDetailsList
    }

    fun changePassword(token: String,changePassword: ChangePassword){

        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.changePassword(token,changePassword )
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {

                if (response.isSuccessful){

                    changePasswordLD.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                changePasswordLD.postValue(null)
            }

        })
    }
    fun getDonorDetails(token: String,userID: String){

        val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.getDonorDetails(token,userID )
        call.enqueue(object : Callback<DonorInfoResponse> {
            override fun onResponse(
                call: Call<DonorInfoResponse>,
                response: Response<DonorInfoResponse>
            ) {

                if (response.isSuccessful){

                    donorDetailsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DonorInfoResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                donorDetailsList.postValue(null)
            }

        })
    }
}
