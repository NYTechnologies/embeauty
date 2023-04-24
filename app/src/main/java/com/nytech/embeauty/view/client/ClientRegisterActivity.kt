package com.nytech.embeauty.view.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivityClientRegisterBinding

/**
 * Tela de Cadastro para cliente
 */
class ClientRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientRegisterBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para tela de login do cliente
        binding.textLoginClient.setOnClickListener {
            startActivity(Intent(this@ClientRegisterActivity, ClientLoginActivity::class.java))
        }
    }
}