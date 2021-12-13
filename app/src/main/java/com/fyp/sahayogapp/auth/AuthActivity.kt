package com.fyp.sahayogapp.auth

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.permissions.PermissionAlerts
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.fyp.sahayogapp.permissions.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AuthActivity : AppCompatActivity() {
    companion object {
        const val REMEMBER_PREF = "rememberMe"
        const val REMEMBER_KEY = "remember"
        const val MY_PERMISSIONS_REQUEST_CAMERA = 100
        const val ALLOW_KEY = "ALLOWED"
        const val CAMERA_PREF = "camera_pref"
        const val MY_PERMISSIONS_REQUEST_STORAGE = 101
        const val LOCATION_PREF = "location_pref"
        const val MY_PERMISSIONS_REQUEST_LOCATION = 102
        const val STORAGE_PREF = "storage_pref"
        const val FILE_SELECT_CODE = 200
        const val CAM_MESSAGE = "THIS APP REQUIRES ACCESS TO CAMERA"
        const val LOC_MESSAGE = "THIS APP REQUIRES ACCESS TO LOCATION\nPlease provide Location Access to find Donors and routes to Hospital locations. "
        const val STOR_MESSAGE = "THIS APP REQUIRES ACCESS TO DEVICE STORAGE"

        fun View.hideKeyboard(context : Context) {
            val inputMethodManager =
                context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
        }

        fun nav(view: View, id: Int) {
            Navigation.findNavController(view).navigate(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
        PermissionLocation.checkLocationAccess(this,this)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                var i = 0
                val len = permissions.size
                while (i < len) {
                    val permission = permissions[i]
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permission!!
                        )
                        if (showRationale) {
                            PermissionAlerts.showAlert(
                                this,
                                this,
                                LOC_MESSAGE,
                                MY_PERMISSIONS_REQUEST_LOCATION
                            )
                        } else if (!showRationale) {
                            SharedPreferences.saveToPreferences(
                                this,
                                ALLOW_KEY,
                                true,
                                LOCATION_PREF
                            )
                        }
                    }
                    i++
                }
            }
        }
    }




}