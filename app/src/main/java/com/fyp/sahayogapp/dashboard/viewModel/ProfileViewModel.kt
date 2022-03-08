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

private const val TAG = "LoginUserViewModel"
class ProfileViewModel: ViewModel() {

    lateinit var changePasswordLD: MutableLiveData<APIResponse?>
    lateinit var donorDetailsList: MutableLiveData<DonorInfoResponse?>
    lateinit var hospitalDetailsList: MutableLiveData<HospitalInfoResponse?>

    init {
        changePasswordLD = MutableLiveData()
        donorDetailsList = MutableLiveData()
        hospitalDetailsList = MutableLiveData()
    }

    fun passwordChangeObservable(): MutableLiveData<APIResponse?> {

        return changePasswordLD
    }

    fun donorDetailObservable(): MutableLiveData<DonorInfoResponse?> {

        return donorDetailsList
    }

    fun hospitalDetailObservable(): MutableLiveData<HospitalInfoResponse?> {

        return hospitalDetailsList
    }

    fun changePassword(changePassword: ChangePassword) {

        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.changePassword( changePassword)
            changePasswordLD.postValue(call)
        }
    }

    fun getDonorDetails( userID: String) {

        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getDonorDetails( userID)

            donorDetailsList.postValue(call)
        }
    }

    fun getHospitalDetails( userID: String) {

        viewModelScope.launch(IO) {
            val retrofitInstance =
                RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getHospitalDetails( userID)

            hospitalDetailsList.postValue(call)
        }
    }
}