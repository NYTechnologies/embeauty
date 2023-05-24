package com.nytech.embeauty.view.salon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.constants.IntentConstants
import com.nytech.embeauty.databinding.ActivityUpdateServiceBinding
import com.nytech.embeauty.repository.SalonServicesRepository

class UpdateServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateServiceBinding
    private val salonServicesRepository: SalonServicesRepository = SalonServicesRepository()

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

            salonServicesRepository
                .updateSalonServiceByOldName(oldServiceName, newServiceName, newServicePrice) {
                    finish()
            }
        }
    }
}