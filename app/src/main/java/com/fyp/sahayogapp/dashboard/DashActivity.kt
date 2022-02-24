package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast
import android.content.Intent
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.fyp.sahayogapp.base.BaseActivity
import com.fyp.sahayogapp.donation.DonationActivity
import com.fyp.sahayogapp.permissions.PermissionLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DashActivity : BaseActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var backPressedTime: Long = 0;
    lateinit var navBar: BottomNavigationView
    lateinit var requestFloatingActionButton: FloatingActionButton
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        supportActionBar?.hide()
        navBar = findViewById(R.id.bottom_navigation_view)
        requestFloatingActionButton = findViewById(R.id.fab)
        PermissionLocation.checkLocationAccess(this,this)
        navController = findNavController(R.id.dashboard_host_fragment)
        navBar.setupWithNavController(navController!!)

        requestFloatingActionButton.setOnClickListener {

                val intent = Intent(this, DonationActivity::class.java)
                startActivity(intent)
            }
    }




    override fun onBackPressed() {
        if(navBar.selectedItemId==R.id.nav_home) {
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
        else{
            super.onBackPressed()
        }
    }
}
