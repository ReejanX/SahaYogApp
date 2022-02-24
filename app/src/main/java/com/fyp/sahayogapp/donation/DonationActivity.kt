package com.fyp.sahayogapp.donation


import android.os.Bundle
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseActivity


class DonationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        supportActionBar?.hide()
    }
}