package com.fyp.sahayogapp.donation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.sahayogapp.R

class DonationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        supportActionBar?.hide()
    }
}