package com.fyp.sahayogapp.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var pref: SharedPreferences

    private val PREF_NAME = "app.hero"
    private val KEY_ACCESS_TOKEN = "access_token"
    private val KEY_FCM_TOKEN = "fcm_token"
    private val KEY_FCM_TOKEN_SERVER_ID = "fcm_token_server_id"
    private val KEY_USER_ID = "user_id"
    private val WALKTHROUGH_SEEN = "walkthrough_seen"

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

    fun saveUserId(userId: String) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putString(KEY_USER_ID, "$userId")
            commit()
        }
    }

    fun containsAccessToken():Boolean{
        return pref.contains(KEY_ACCESS_TOKEN)
    }

    fun containsFcmToken(): Boolean {
        return pref.contains(KEY_FCM_TOKEN)
    }

    fun setWalkThroughStatusSeen(isSeen: Boolean) {
        val p: SharedPreferences.Editor = pref.edit()
        with(p) {
            putBoolean(WALKTHROUGH_SEEN, isSeen)
            commit()
        }
    }

    fun getWalkThroughSeenStatus() = pref.getBoolean(WALKTHROUGH_SEEN,false)

    fun getAccessToken() = pref.getString(KEY_ACCESS_TOKEN, null)
    fun getFcmToken() = pref.getString(KEY_FCM_TOKEN, null)
    fun getUserId() = pref.getString(KEY_USER_ID, null)
    fun getFcmTokenServerId() = pref.getString(KEY_FCM_TOKEN_SERVER_ID, null)

    fun clear() {
        editor.run {
            editor.clear()
        }.commit()
    }


}