package com.fyp.sahayogapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.fyp.sahayogapp.utils.Conts.KEY_ACCESS_TOKEN
import com.fyp.sahayogapp.utils.Conts.KEY_FCM_TOKEN
import com.fyp.sahayogapp.utils.Conts.KEY_FCM_TOKEN_SERVER_ID
import com.fyp.sahayogapp.utils.Conts.KEY_USER_ID
import com.fyp.sahayogapp.utils.Conts.KEY_USER_ROLE
import com.fyp.sahayogapp.utils.Conts.PREF_NAME
import com.fyp.sahayogapp.utils.Conts.WALKTHROUGH_SEEN

object PreferenceHelper {
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var pref: SharedPreferences



    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref!!.edit()
    }

    fun saveAccessToken(token: String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_ACCESS_TOKEN, "$token")
            commit()
        }
    }
    fun saveFcmTokenServerId(token: String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_FCM_TOKEN_SERVER_ID, "$token")
            commit()
        }
    }

    fun saveFcmToken(token: String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_FCM_TOKEN, "$token")
            commit()
        }
    }

    fun saveUserId(userId: String, userRole: String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_USER_ID, "$userId")
            putString(KEY_USER_ROLE, "$userRole")
            commit()
        }
    }

    fun containsAccessToken():Boolean{
        return pref.contains(KEY_ACCESS_TOKEN)
    }

    fun containsFcmToken(): Boolean {
        return pref.contains(KEY_FCM_TOKEN)
    }


    fun getAccessToken() = pref.getString(KEY_ACCESS_TOKEN, null)
    fun getFcmToken() = pref.getString(KEY_FCM_TOKEN, null)
    fun getUserId() = pref.getString(KEY_USER_ID, null)
    fun getUserRole() = pref.getString(KEY_USER_ROLE, null)
    fun getFcmTokenServerId() = pref.getString(KEY_FCM_TOKEN_SERVER_ID, null)

    fun clear() {
        editor.run {
            editor.clear()
        }.commit()
    }


}