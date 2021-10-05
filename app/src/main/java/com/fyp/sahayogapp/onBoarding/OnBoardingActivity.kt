package com.fyp.sahayogapp.onBoarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.onBoarding.adapter.ViewPagerAdapter
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var detailList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()
    var firstOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        supportActionBar?.hide()
        val btnNext: Button = findViewById(R.id.next)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        postToList()

        viewPager.adapter = ViewPagerAdapter(titleList, detailList, imageList)
        val circleIndicator = findViewById<CircleIndicator3>(R.id.indicator)
        circleIndicator.setViewPager(viewPager)
        val btnSkip: Button = findViewById(R.id.skipBtn)

        btnSkip.setOnClickListener {
            onBoardingFinished()
            startActivity(Intent(this, AuthActivity::class.java))
        }
        btnNext.setOnClickListener {
            if (viewPager.currentItem == 2) {
                onBoardingFinished()

                startActivity(Intent(this, AuthActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            } else {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true);
            }
        }
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                super.onPageSelected(position)
                when (position) {
                    0, 1 -> {
                        btnNext.text = "Next"
                        btnSkip.visibility = View.VISIBLE
                    }
                    else -> {
                        btnNext.text = "Get Started"
                        btnSkip.visibility = View.INVISIBLE
                    }

                }

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })


    }

    private fun onBoardingFinished() {
        val sharedPreferences =
            this.getSharedPreferences("onBoardingFinished", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    private fun postToList() {
        addToList(
            "Donate and Request Blood",
            "Donate Blood and Save Life \n in just one Step",
            R.drawable.blood_donation
        )
        addToList(
            "Make Donation Appointments",
            "Can't Donate right away \n Make an Appointment to donate",
            R.drawable.calendar
        )
        addToList(
            "Hospital? Manage Donations",
            "Make bulk Blood donation requests \n and donate to Blood banks and Hospitals",
            R.drawable.hospital
        )
    }

    private fun addToList(title: String, description: String, image: Int) {
        titleList.add(title)
        detailList.add(description)
        imageList.add(image)


    }

}
