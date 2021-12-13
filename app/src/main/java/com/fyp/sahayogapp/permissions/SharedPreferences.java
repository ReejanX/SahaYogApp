package com.fyp.sahayogapp.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class SharedPreferences {
    public static void startInstalledAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }


    public static Boolean getFromPref(Context context, String key, String pref) {
        android.content.SharedPreferences myPrefs = context.getSharedPreferences
                (pref, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed, String pref) {
        android.content.SharedPreferences myPrefs = context.getSharedPreferences
                (pref, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }
}
