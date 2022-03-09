package com.fyp.sahayogapp.dashboard.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RequestViewModel"
class RequestViewModel : ViewModel(){

    lateinit var recylerlistData: MutableLiveData<DonationRequestResponse>

    init {
        recylerlistData = MutableLiveData()
    }

    fun getDonationListObserver(): MutableLiveData<DonationRequestResponse>{

        return recylerlistData
    }

    fun getDonationList(){

        viewModelScope.launch(IO){
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getAllRequestList()
            recylerlistData.postValue(call)

        }
    }
    lateinit var venueList: MutableLiveData<List<VenueData>>

    init {
        venueList = MutableLiveData()
    }

    fun getVenueObserver(): MutableLiveData<List<VenueData>>{

        return venueList
    }

    fun getVenueList(){

        viewModelScope.launch(IO){
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getVenues()
            venueList.postValue(call)

        }
    }

    lateinit var donationPost: MutableLiveData<APIResponse>

    init {
        donationPost= MutableLiveData()
    }

    fun postDonationObserver(): MutableLiveData<APIResponse>{

        return donationPost
    }

    fun postDonation(donationRequest :DonationRequestModel){

        viewModelScope.launch(IO) {
            val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.postDonationRequest(donationRequest)
            donationPost.postValue(call)
        }
    }

    lateinit var acceptDonation: MutableLiveData<APIResponse?>

    init {
        acceptDonation= MutableLiveData()
    }

    fun acceptDonationObserver(): MutableLiveData<APIResponse?>{

        return acceptDonation
    }

    fun acceptRequest(donationRequest :AcceptDonation){

        viewModelScope.launch(IO) {
            val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.acceptDonationRequest(donationRequest)
            acceptDonation.postValue(call)
        }
    }


}


