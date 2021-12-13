package com.fyp.sahayogapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.fyp.sahayogapp.auth.AuthActivity.Companion.ALLOW_KEY
import com.fyp.sahayogapp.auth.AuthActivity.Companion.CAMERA_PREF
import com.fyp.sahayogapp.auth.AuthActivity.Companion.CAM_MESSAGE
import com.fyp.sahayogapp.auth.AuthActivity.Companion.LOCATION_PREF
import com.fyp.sahayogapp.auth.AuthActivity.Companion.LOC_MESSAGE
import com.fyp.sahayogapp.auth.AuthActivity.Companion.MY_PERMISSIONS_REQUEST_CAMERA
import com.fyp.sahayogapp.auth.AuthActivity.Companion.MY_PERMISSIONS_REQUEST_LOCATION
import com.fyp.sahayogapp.auth.AuthActivity.Companion.MY_PERMISSIONS_REQUEST_STORAGE
import com.fyp.sahayogapp.auth.AuthActivity.Companion.STORAGE_PREF
import com.fyp.sahayogapp.auth.AuthActivity.Companion.STOR_MESSAGE
import com.fyp.sahayogapp.permissions.PermissionAlerts
import com.fyp.sahayogapp.permissions.SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}