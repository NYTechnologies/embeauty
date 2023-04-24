package com.nytech.embeauty.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.R
import com.nytech.embeauty.databinding.ActivityClientLoginBinding

/**
 * Tela de Login para cliente
 */
class ClientLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //botão para tela de cadastro de cliente
        binding.textSignupClient.setOnClickListener {
            val intent = Intent(this@ClientLoginActivity, ClientRegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}