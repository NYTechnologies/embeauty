package com.nytech.embeauty.view.salon

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.nytech.embeauty.R
import com.nytech.embeauty.constants.ToastTextConstants
import com.nytech.embeauty.databinding.ActivityUpdateAppointmentBinding
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.repository.SalonAppointmentsRepository
import com.nytech.embeauty.repository.SalonServicesRepository
import java.text.SimpleDateFormat
import java.util.*

class UpdateAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateAppointmentBinding
    private val salonAppointmentsRepository: SalonAppointmentsRepository =
        SalonAppointmentsRepository()
    private val calendar: Calendar = Calendar.getInstance()
    private val salonServicesRepository: SalonServicesRepository = SalonServicesRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var servicesName = emptyList<String>()

        salonServicesRepository.getSalonServices { salonServices ->
            servicesName = salonServices.services.map { it.name } + "Outros"
        }

        // Captura os atributos do agendamento a ser editado vindo do INTENT
        val appointmentUUID = intent.getStringExtra("uuid").toString()
        val oldClientName = intent.getStringExtra("old_client_name").toString()
        val oldClientPhone = intent.getStringExtra("old_client_phone").toString()
        val oldStartDateTime = intent.getStringExtra("old_start_date_time").toString()
        val oldDuration = intent.getStringExtra("old_duration").toString()
        val oldServiceName = intent.getStringExtra("old_service_name").toString()

        Log.d(
            "UpdateAppointmentActivity",
            "$appointmentUUID, $oldClientName, $oldClientPhone, $oldStartDateTime, $oldDuration, $oldServiceName"
        )

        // Popula os campos de input com os valores que estavam preenchidos anteriormente
        binding.editUpdateClientName.setText(oldClientName)
        binding.editUpdateClientPhone.setText(oldClientPhone)
        binding.editUpdateDateTime.setText(oldStartDateTime)
        binding.editUpdateServiceDuration.setText(oldDuration)
        binding.editUpdateServiceNameAppointment.setText(oldServiceName)

        // Listener do campo de Data
        binding.editUpdateDateTime.setOnClickListener {
            showDateTimePicker()
        }

        // Listener do campo de Nome do Serviço
        binding.editUpdateServiceNameAppointment.isFocusable = false
        binding.editUpdateServiceNameAppointment.setOnClickListener {
            showSalonServices(servicesName)
        }

        // Lógica do botão para enviar a edição do agendamento
        binding.buttonEditAppointment.setOnClickListener {
            // Armazenar os dados digitados nos campos em variáveis
            val newClientName = binding.editUpdateClientName.text.toString()
            val newClientPhone = binding.editUpdateClientPhone.text.toString().trim { it <= ' ' }
            val newStartDateTime = binding.editUpdateDateTime.text.toString()
            val newServiceDuration =
                binding.editUpdateServiceDuration.text.toString().trim { it <= ' ' }
            val newServiceName = binding.editUpdateServiceNameAppointment.text.toString()

            //checa se o nome do service foi colocado e notifica caso não tenha sido
            if (newServiceName.isEmpty()) {
                Toast.makeText(
                    this@UpdateAppointmentActivity,
                    ToastTextConstants.POR_FAVOR_INSIRA_O_NOME_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a duração do service foi colocado e notifica caso não tenha sido
            if (newServiceDuration.isEmpty()) {
                Toast.makeText(
                    this@UpdateAppointmentActivity,
                    ToastTextConstants.POR_FAVOR_INSIRA_A_DURACAO_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a data do service foi colocada e notifica caso não tenha sido
            if (newStartDateTime.isEmpty()) {
                Toast.makeText(
                    this@UpdateAppointmentActivity,
                    ToastTextConstants.POR_FAVOR_INSIRA_A_DATA_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Instancia um Appointment com as informações passadas (com UUID), e chama suas funções para setar as outras propriedades
            val appointmentToBeUpdated = SalonAppointments.Appointment(
                uuid = appointmentUUID,
                clientName = newClientName,
                clientPhone = newClientPhone,
                startDateTime = newStartDateTime,
                durationMinutes = newServiceDuration.toInt(),
                serviceName = newServiceName
            ).apply {
                setsStartTimestamp(this.startDateTime)
                setEndParameters(this.startTimestamp!!, this.durationMinutes)
            }

            salonAppointmentsRepository.updateSalonAppointment(
                this@UpdateAppointmentActivity,
                appointmentToBeUpdated,
                oldStartDateTime
            ) {
                finish()
            }
        }
    }

    private fun showSalonServices(salonServices: List<String>) {
        var selectedIndex = 0
        val servicesName = salonServices.toTypedArray()
        var selectedService = servicesName[selectedIndex]
        val servicesDialog = AlertDialog.Builder(this)
            .setTitle("Escolha o serviço:")
            .setSingleChoiceItems(servicesName, selectedIndex) { _, which ->
                selectedIndex = which
                selectedService = servicesName[selectedIndex]
            }
            .setPositiveButton("OK") { _, _ ->
                binding.editUpdateServiceNameAppointment.setText(selectedService)
                binding.editUpdateServiceNameAppointment.isFocusable = true
            }
            .setNegativeButton("Cancelar") { _, _ ->
                binding.editUpdateServiceNameAppointment.isFocusable = true
            }
        servicesDialog.show()
    }

    private fun showDateTimePicker() {
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                showTimePicker(currentHour, currentMinute)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(hour: Int, initialMinute: Int) {
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val selectedDateTime = calendar.time
                val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
                val formattedDateTime = sdf.format(selectedDateTime)

                binding.editUpdateDateTime.setText(formattedDateTime)
                binding.tilUpdateDateTime.isErrorEnabled = false
            },
            hour,
            initialMinute,
            true
        )

        timePickerDialog.show()
    }

    fun registerNewAppointmentSuccess() {
        finish()
    }
}