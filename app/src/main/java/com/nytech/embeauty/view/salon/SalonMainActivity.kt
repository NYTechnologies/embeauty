package com.nytech.embeauty.view.salon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nytech.embeauty.R

class SalonMainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var currentFragmentId: Int = -1 // Keep track of the current fragment ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.activity_salon_main)

        // captura o fragmento salon_main_container pelo id
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.salon_main_container) as NavHostFragment
        navController = navHostFragment.navController

        // captura a salon_bottom_navbar pelo id
        val salonBottonNavBar = findViewById<BottomNavigationView>(R.id.salon_bottom_navbar)

        setupWithNavController(salonBottonNavBar, navController)

        currentFragmentId = savedInstanceState?.getInt(CURRENT_FRAGMENT_ID) ?: -1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_FRAGMENT_ID, currentFragmentId)
    }

    override fun onResume() {
        super.onResume()
        when (currentFragmentId) {
            R.id.salonHomeFragment -> {
                navController.navigate(R.id.salonHomeFragment)
            }
            R.id.salonAppointmentFragment -> {
                navController.navigate(R.id.salonAppointmentFragment)
            }
            R.id.salonServicesFragment -> {
                navController.navigate(R.id.salonServicesFragment)
            }
            R.id.salonSettingsFragment -> {
                navController.navigate(R.id.salonSettingsFragment)
            }
        }
    }

    companion object {
        private const val CURRENT_FRAGMENT_ID = "current_fragment_id"
    }
}
