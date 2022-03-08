package com.fyp.sahayogapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.fyp.sahayogapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

public class FCMNotificationService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
       var title =  p0.notification?.title
       var body =  p0.notification?.body
        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
        var notificaitonChannel = NotificationChannel(
            CHANNEL_ID,
            "Heads up notificaiton",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(notificaitonChannel)
       var notification: Notification.Builder = Notification.Builder(this,CHANNEL_ID)
           .setContentTitle(title)
           .setContentText(body)
           .setSmallIcon(R.drawable.blood)
           .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(1,notification.build())
        super.onMessageReceived(p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("MY tag",p0)
    }
}