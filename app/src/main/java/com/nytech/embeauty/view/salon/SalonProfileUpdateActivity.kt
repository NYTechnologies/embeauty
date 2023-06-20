package com.nytech.embeauty.view.salon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.R
import com.nytech.embeauty.databinding.ActivitySalonProfileUpdateBinding
import com.nytech.embeauty.databinding.ActivityUpdateAppointmentBinding
import com.nytech.embeauty.model.SalonProfile
import com.nytech.embeauty.repository.SalonProfileRepository

class SalonProfileUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonProfileUpdateBinding
    private val salonProfileRepository: SalonProfileRepository = SalonProfileRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //captura
        salonProfileRepository.getSalonProfile { oldSalonProfile ->
            //popula o input
            binding.editUpdateSalonName.setText(oldSalonProfile.name)
            binding.editUpdateSalonPhone.setText(oldSalonProfile.phone)

            //lógica do botão q captura as novas infos colocadas nos inputs
            binding.buttonUpdateProfile.setOnClickListener {
                //armazena os dados digitados nos campos em variáveis
                val newSalonName = binding.editUpdateSalonName.text.toString()
                val newSalonPhone = binding.editUpdateSalonPhone.text.toString().trim { it <= ' ' }

                //mix das infos velhas com as infos novas do usuário
                val newSalonProfile = SalonProfile(
                    firebaseUID = oldSalonProfile.firebaseUID,
                    name = newSalonName,
                    phone = newSalonPhone,
                    email = oldSalonProfile.email
                )

                //chama a classe do repositório passando as novas infos
                salonProfileRepository.updateSalon(this@SalonProfileUpdateActivity, newSalonProfile)
            }
        }

    }

    //encerra a activity de edição do salão (Salon Profile)
    fun salonUpdateSuccess() {
        finish()
    }
}