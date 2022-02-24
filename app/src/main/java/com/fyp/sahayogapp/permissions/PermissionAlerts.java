package com.fyp.sahayogapp.permissions;



import static com.fyp.sahayogapp.auth.AuthActivity.MY_PERMISSIONS_REQUEST_LOCATION;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.fyp.sahayogapp.auth.AuthActivity;

public class PermissionAlerts {
    public static void showSettingsAlert(Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Permission Denied : App will Close soon", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                        delayedExit(context);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferences.startInstalledAppDetailsActivity(context);

                    }
                });
        alertDialog.show();
    }

    public static void showPAlert(Context context, Activity activity, String message, int requestCode) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Permission Denied : App will Close soon", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                        delayedExit(context);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                });
        alertDialog.show();
    }

    private static void delayedExit(Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 3000);
    }

}

