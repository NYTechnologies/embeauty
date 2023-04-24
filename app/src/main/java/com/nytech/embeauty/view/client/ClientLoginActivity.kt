package com.nytech.embeauty.view.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.databinding.ActivityClientLoginBinding

/**
 * Tela de Login para cliente
 */
class ClientLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientLoginBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)


        //botão para tela de cadastro de cliente
        binding.textSignupClient.setOnClickListener {
            val intent = Intent(this@ClientLoginActivity, ClientRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
