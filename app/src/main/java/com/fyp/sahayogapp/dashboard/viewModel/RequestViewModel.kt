package com.fyp.sahayogapp.dashboard.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RequestViewModel"
class RequestViewModel : ViewModel(){

    lateinit var recylerlistData: MutableLiveData<List<DonationRequestModel>>

    init {
        recylerlistData = MutableLiveData()
    }

    fun getDonationListObserver(): MutableLiveData<List<DonationRequestModel>>{

        return recylerlistData
    }

    fun getDonationList(){

        val retrofitInstance =RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
        val call = retrofitInstance.getAllRequestList()
        call.enqueue(object : Callback<List<DonationRequestModel>>{
            override fun onResponse(
                call: Call<List<DonationRequestModel>>,
                response: Response<List<DonationRequestModel>>
            ) {
                if (response.isSuccessful){

                    recylerlistData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<DonationRequestModel>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
               recylerlistData.postValue(null)
            }

        })
    }
}


