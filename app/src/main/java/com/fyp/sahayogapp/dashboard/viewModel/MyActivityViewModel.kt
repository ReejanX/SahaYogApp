package com.fyp.sahayogapp.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.DonationRequestResponse
import kotlinx.coroutines.launch

class MyActivityViewModel : ViewModel() {

    lateinit var acceptedRequestList: MutableLiveData<DonationRequestResponse>

    init {
        acceptedRequestList = MutableLiveData()
    }

    fun getAcceptedRequestObserver(): MutableLiveData<DonationRequestResponse> {

        return acceptedRequestList
    }

    fun getAcceptedRequest(donorID:String){

        viewModelScope.launch {
            val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getMyAcceptedDonations(donorID)
            acceptedRequestList.postValue(call)
        }
    }

    lateinit var myRequestList:MutableLiveData<DonationRequestResponse>

    init{
        myRequestList = MutableLiveData()
    }

    fun getMyRequestsObserver(): MutableLiveData<DonationRequestResponse>{
        return myRequestList
    }
    fun getMyRequests(userID:String){
        viewModelScope.launch {
            val retrofitClient = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitClient.getMyRequests(userID)
            myRequestList.postValue(call)
        }
    }
}