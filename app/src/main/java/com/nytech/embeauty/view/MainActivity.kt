package com.nytech.embeauty.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //botões página inicial
        binding.buttonLandingPageSalon.setOnClickListener {
            startActivity(Intent(this@MainActivity, SalonEntryActivity::class.java))
        }
        binding.buttonLandingPageClient.setOnClickListener {
            startActivity(Intent(this@MainActivity, ClientEntryActivity::class.java))
        }
    }
}
