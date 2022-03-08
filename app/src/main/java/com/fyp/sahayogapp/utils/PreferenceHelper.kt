package com.fyp.sahayogapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.fyp.sahayogapp.utils.Conts.KEY_ACCESS_TOKEN
import com.fyp.sahayogapp.utils.Conts.KEY_FCM_TOKEN
import com.fyp.sahayogapp.utils.Conts.KEY_FCM_TOKEN_SERVER_ID
import com.fyp.sahayogapp.utils.Conts.KEY_ROLE_ID
import com.fyp.sahayogapp.utils.Conts.KEY_USER_ID
import com.fyp.sahayogapp.utils.Conts.KEY_USER_NAME
import com.fyp.sahayogapp.utils.Conts.KEY_USER_ROLE
import com.fyp.sahayogapp.utils.Conts.PREF_NAME
import com.fyp.sahayogapp.utils.Conts.WALKTHROUGH_SEEN

lateinit var editor: SharedPreferences.Editor
lateinit var pref: SharedPreferences


object PreferenceHelper {


    fun initPref(context: Context) {
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

    fun saveUserId(userId: String, userRole: String,typeID:String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_ROLE, userRole)
            putString(KEY_ROLE_ID, typeID)
            commit()
        }
    }

    fun containsAccessToken():Boolean{
        return pref.contains(KEY_ACCESS_TOKEN)
    }

    fun containsFcmToken(): Boolean {
        return pref.contains(KEY_FCM_TOKEN)
    }


    fun getAccessToken() = pref.getString(KEY_ACCESS_TOKEN, "token")
    fun getFcmToken() = pref.getString(KEY_FCM_TOKEN, null)
    fun getUserId() = pref.getString(KEY_USER_ID, null)
    fun getUserRole() = pref.getString(KEY_USER_ROLE, null)
    fun getRoleID() = pref.getString(KEY_ROLE_ID, null)
    fun getFcmTokenServerId() = pref.getString(KEY_FCM_TOKEN_SERVER_ID, null)

    fun clearPref() {
        editor.run {
            editor.clear()
        }.commit()
    }


}