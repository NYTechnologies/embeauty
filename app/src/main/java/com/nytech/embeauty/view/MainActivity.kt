package com.nytech.embeauty.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivityMainBinding
import com.nytech.embeauty.view.client.ClientLoginActivity
import com.nytech.embeauty.view.salon.SalonLoginActivity

/**
 * Tela inicial da aplicação
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)

        //botões página inicial
        binding.buttonLandingPageSalon.setOnClickListener {
            val intent = Intent(this@MainActivity, SalonLoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonLandingPageClient.setOnClickListener {
            val intent = Intent(this@MainActivity, ClientLoginActivity::class.java)
            startActivity(intent)
        }
    }
}
