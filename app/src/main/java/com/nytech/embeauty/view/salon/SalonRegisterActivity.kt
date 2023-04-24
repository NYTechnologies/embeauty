package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivitySalonRegisterBinding

/**
 * Tela de Cadastro para salão
 */
class SalonRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonRegisterBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para a tela de login do salão
        binding.textLoginSalon.setOnClickListener {
            startActivity(Intent(this@SalonRegisterActivity, SalonLoginActivity::class.java))
        }
    }
}
