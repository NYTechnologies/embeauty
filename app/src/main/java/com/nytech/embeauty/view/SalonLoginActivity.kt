package com.nytech.embeauty.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.R
import com.nytech.embeauty.databinding.ActivityClientLoginBinding
import com.nytech.embeauty.databinding.ActivitySalonLoginBinding

/**
 * Tela de Login para salão
 */
class SalonLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // botão para tela de cadastro de salão
        binding.textSignupSalon.setOnClickListener {
            val intent = Intent(this@SalonLoginActivity, SalonRegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}