package com.fyp.sahayogapp.notification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.sahayogapp.api.ApiInterface
import com.fyp.sahayogapp.api.RetrofitClient
import com.fyp.sahayogapp.dashboard.model.APIResponse
import com.fyp.sahayogapp.dashboard.model.FCMData
import com.fyp.sahayogapp.dashboard.model.NotificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {

    lateinit var notificaionList: MutableLiveData<NotificationResponse?>

    init {
        notificaionList= MutableLiveData()
    }

    fun getNotificationObserver(): MutableLiveData<NotificationResponse?> {

        return notificaionList
    }

    fun getNotifications(id: String){

        viewModelScope.launch(Dispatchers.IO) {
            val retrofitInstance = RetrofitClient.getRetrofitInstance().create(ApiInterface::class.java)
            val call = retrofitInstance.getNotifications(id)
            notificaionList.postValue(call)
        }
    }

}