package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast
import android.content.Intent
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.google.android.gms.location.FusedLocationProviderClient


class DashActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var backPressedTime: Long = 0;
    lateinit var navBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        supportActionBar?.hide()
        navBar = findViewById(R.id.bottom_navigation_view)
        PermissionLocation.checkLocationAccess(this,this)
        navBar.menu.getItem(2).isEnabled = false
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer, HomeFragment())
            .commit()
        navBar.setOnNavigationItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.nav_home -> {
                    setFragment(HomeFragment())
                    true
                }

                R.id.nav_report -> {
                    setFragment(ReportFragment())
                    true
                }

                R.id.nav_profile -> {
                    setFragment(ProfileFragment())
                    true
                }
                R.id.nav_more -> {
                    setFragment(SettingsFragment())
                    true
                }

                else -> false
            }
        }

    }

    fun setFragment(fr: Fragment) {
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.frameContainer, fr)
        frag.commit()
    }

    override fun onBackPressed() {
        val t: Long = System.currentTimeMillis()
        if (t - backPressedTime > 2000) {
            backPressedTime = t
            Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show()
        } else {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
    }
}
