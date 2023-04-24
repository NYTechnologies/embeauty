package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivitySalonLoginBinding

/**
 * Tela de Login para salão
 */
class SalonLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonLoginBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para tela de cadastro de salão
        binding.textSignupSalon.setOnClickListener {
            val intent = Intent(this@SalonLoginActivity, SalonRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
