package com.fyp.sahayogapp.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseActivity
import com.fyp.sahayogapp.dashboard.adapters.RequestListAdapter
import com.fyp.sahayogapp.dashboard.model.NotificationData
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.notification.adapter.NotificationListAdapter
import com.fyp.sahayogapp.notification.viewmodel.NotificationViewModel
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import okhttp3.internal.notifyAll


private lateinit var backBtn: ImageButton
private lateinit var notificationRecycler: RecyclerView
private lateinit var notificationViewModel : NotificationViewModel
lateinit var notificationListAdapter: NotificationListAdapter
var userId = getUserId()
class NotificationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        supportActionBar?.hide()
        backBtn = findViewById(R.id.backBtn)
        notificationRecycler = findViewById(R.id.notification_recyler)

        notificationListAdapter = NotificationListAdapter()
        initViewModel()
        initRecyclerView()
        backBtn.setOnClickListener {
            finish()
        }

    }

    private fun initRecyclerView() {
        notificationRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notificationListAdapter
        }
    }

    private fun initViewModel() {
        notificationViewModel= ViewModelProvider(this).get(NotificationViewModel::class.java)

        notificationViewModel.getNotificationObserver().observe(this,{
            if (it==null){
                showAlert("Failed",it?.message)

            }
            if(it?.code == "200"){
                    notificationListAdapter.notificationList= it.data.toMutableList()
                    notificationListAdapter.notifyDataSetChanged()
            }
        })

        notificationViewModel.getNotifications(userId!!)
    }
}