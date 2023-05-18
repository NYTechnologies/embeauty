package com.nytech.embeauty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.nytech.embeauty.constants.IntentConstants
import com.nytech.embeauty.databinding.ActivityUpdateServiceBinding
import com.nytech.embeauty.repository.SalonRepository
import com.nytech.embeauty.view.salon.SalonMainActivity

class UpdateServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateServiceBinding
    private val salonRepository: SalonRepository = SalonRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // captura o nome e preço antigo do serviço a ser editado
        val oldServiceName = intent.getStringExtra(IntentConstants.OLD_SERVICE_NAME).toString()
        val oldServicePrice = intent.getStringExtra(IntentConstants.OLD_SERVICE_PRICE).toString()

        binding.editServiceNameUpdate.setText(oldServiceName)
        binding.editServicePriceUpdate.setText(oldServicePrice)

        binding.buttonUpdateService.setOnClickListener {

            val newServiceName = binding.editServiceNameUpdate.text.toString()
            val newServicePrice = binding.editServicePriceUpdate.text.toString()

            salonRepository
                .updateServiceByOldName(oldServiceName, newServiceName, newServicePrice) {
                    // se o cadastro do novo serviço for completado com sucesso, voltar a SalonMainActivity
                    val intent = Intent(this@UpdateServiceActivity, SalonMainActivity::class.java)

                    // Envia para o SalonMainActivity dizendo para ir para o Fragment de Serviços
                    intent.putExtra(IntentConstants.TARGET_FRAGMENT, IntentConstants.SALON_SERVICES_FRAGMENT)
                    startActivity(intent)
                    finish()
            }
        }
    }
}