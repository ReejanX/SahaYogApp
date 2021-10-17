package com.fyp.sahayogapp.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashActivity : AppCompatActivity() {
    lateinit var navBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        supportActionBar?.hide()
        navBar= findViewById(R.id.bottom_navigation_view)

        navBar.menu.getItem(2).isEnabled = false
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer,HomeFragment()).commit()
        navBar.setOnNavigationItemSelectedListener {menu ->

            when(menu.itemId){

                R.id.nav_home-> {
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
                R.id.nav_setting -> {
                    setFragment(SettingsFragment())
                    true
                }

                else -> false
            }
        }

        }
    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.frameContainer,fr)
        frag.commit()
    }
    }
