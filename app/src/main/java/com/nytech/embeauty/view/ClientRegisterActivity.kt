package com.nytech.embeauty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.R
import com.nytech.embeauty.databinding.ActivityClientRegisterBinding

/**
 * Tela de Cadastro para cliente
 */
class ClientRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // botão para tela de login

    }
}