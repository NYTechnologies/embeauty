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
    }
}
