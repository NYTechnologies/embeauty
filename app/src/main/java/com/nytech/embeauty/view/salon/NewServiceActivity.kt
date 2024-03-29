package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nytech.embeauty.constants.IntentConstants
import com.nytech.embeauty.constants.ToastTextConstants
import com.nytech.embeauty.databinding.ActivityNewServiceBinding
import com.nytech.embeauty.model.SalonServices
import com.nytech.embeauty.repository.SalonServicesRepository

class NewServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewServiceBinding
    private val salonServicesRepository: SalonServicesRepository = SalonServicesRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityNewServiceBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // pega a lista de services via repository e guarda o NOME de cada service numa nova lista servicesNameList
        val servicesNameList: MutableList<String> = mutableListOf()
        salonServicesRepository.getSalonServices { services ->
            services.services.map { servicesNameList.add(it.name) }
        }

        // botão de adicionar novo serviço
        binding.buttonNewService.setOnClickListener {

            // armazenar os dados digitados no campos de nome, duração e preço
            val serviceName = binding.editServiceName.text.toString()
            val servicePrice = binding.editServicePrice.text.toString()

            // checa se o nome do serviço foi colocado
            if (serviceName.isEmpty()) {
                Toast.makeText(
                    this@NewServiceActivity,
                    ToastTextConstants.POR_FAVOR_INSIRA_O_NOME_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // checa se o nome do serviço já existe
            if (serviceName in servicesNameList) {
                Toast.makeText(
                    this@NewServiceActivity,
                    ToastTextConstants.SERVICO_JA_CADASTRADO,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // checa se o preço do serviço foi colocado
            if (servicePrice.trim().removePrefix("R$").isEmpty()) {
                Toast.makeText(
                    this@NewServiceActivity,
                    ToastTextConstants.POR_FAVOR_INSIRA_O_PRECO_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val newService = SalonServices.Service(
                name = serviceName,
                price = servicePrice
            )

            salonServicesRepository.registerNewSalonService(this@NewServiceActivity, newService)
        }
    }

    fun registerNewServiceSuccess() {
        finish()
    }
}
