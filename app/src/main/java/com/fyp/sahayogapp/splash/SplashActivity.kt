package com.fyp.sahayogapp.splash

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.onBoarding.OnBoardingActivity
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.fyp.sahayogapp.utils.PreferenceHelper.initPref


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        initPref(applicationContext)
        if (!onBoardingFinished()) {


            val secondsDelayed: Int = 1
            Handler().postDelayed(Runnable {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()
            }, (secondsDelayed * 1000).toLong())
        } else {
            val secondsDelayed: Int = 1
            Handler().postDelayed(Runnable {
                startActivity(Intent(this, AuthActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()
            }, (secondsDelayed * 1000).toLong())
        }

    }
    private fun onBoardingFinished():Boolean {
        val sharedPreferences = this.getSharedPreferences("onBoardingFinished", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Finished",false)
    }


}
