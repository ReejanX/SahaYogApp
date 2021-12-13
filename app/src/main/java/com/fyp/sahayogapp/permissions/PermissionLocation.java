package com.fyp.sahayogapp.permissions;


import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import static com.fyp.sahayogapp.auth.AuthActivity.ALLOW_KEY;
import static com.fyp.sahayogapp.auth.AuthActivity.LOCATION_PREF;
import static com.fyp.sahayogapp.auth.AuthActivity.LOC_MESSAGE;
import static com.fyp.sahayogapp.auth.AuthActivity.MY_PERMISSIONS_REQUEST_LOCATION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionLocation {


    public static void checkLocationAccess(Activity activity, Context myContext) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (SharedPreferences.getFromPref(activity, ALLOW_KEY, LOCATION_PREF)) {
                PermissionAlerts.showSettingsAlert(activity, LOC_MESSAGE);


            } else if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    PermissionAlerts.showAlert(myContext, activity, LOC_MESSAGE, MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        } else {
            getLocation();
        }

    }


}
