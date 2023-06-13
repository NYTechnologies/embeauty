package com.nytech.embeauty.view.salon

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_A_DATA_DO_SERVICE
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_A_DURACAO_DO_SERVICE
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_O_NOME_DO_SERVICE
import com.nytech.embeauty.databinding.ActivityNewAppointmentBinding
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.model.SalonServices
import com.nytech.embeauty.repository.SalonAppointmentsRepository
import com.nytech.embeauty.repository.SalonServicesRepository
import java.text.SimpleDateFormat
import java.util.*

class NewAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewAppointmentBinding
    private val calendar: Calendar = Calendar.getInstance()
    private val salonAppointmentsRepository: SalonAppointmentsRepository =
        SalonAppointmentsRepository()
    private val salonServicesRepository: SalonServicesRepository = SalonServicesRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityNewAppointmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var servicesName = emptyList<String>()

        salonServicesRepository.getSalonServices { salonServices ->
            servicesName = salonServices.services.map { it.name } + "Outros"
        }

        binding.editDateTime.setOnClickListener {
            showDateTimePicker()
        }

        binding.editServiceNameAppointment.isFocusable = false

        binding.editServiceNameAppointment.setOnClickListener {
            showSalonServices(servicesName)
        }

        binding.buttonNewAppointment.setOnClickListener {
            // Armazenar os dados digitados nos campos
            val clientName = binding.editClientName.text.toString()
            val clientPhone = binding.editClientPhone.text.toString().trim { it <= ' ' }
            val startDateTime = binding.editDateTime.text.toString()
            val serviceDuration = binding.editServiceDuration.text.toString().trim { it <= ' ' }
            val serviceName = binding.editServiceNameAppointment.text.toString()

            //checa se o nome do service foi colocado e notifica caso não tenha sido
            if (serviceName.isEmpty()) {
                Toast.makeText(
                    this@NewAppointmentActivity,
                    POR_FAVOR_INSIRA_O_NOME_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a duração do service foi colocado e notifica caso não tenha sido
            if (serviceDuration.isEmpty()) {
                Toast.makeText(
                    this@NewAppointmentActivity,
                    POR_FAVOR_INSIRA_A_DURACAO_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a data do service foi colocada e notifica caso não tenha sido
            if (startDateTime.isEmpty()) {
                Toast.makeText(
                    this@NewAppointmentActivity,
                    POR_FAVOR_INSIRA_A_DATA_DO_SERVICE,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Instancia um Appointment com as informações passadas, e chama suas funções para setar as outras propriedades
            val appointment = SalonAppointments.Appointment(
                clientName = clientName,
                clientPhone = clientPhone,
                startDateTime = startDateTime,
                durationMinutes = serviceDuration.toInt(),
                serviceName = serviceName
            ).apply {
                setsStartTimestamp(this.startDateTime)
                setEndParameters(this.startTimestamp!!, this.durationMinutes)
            }

            salonAppointmentsRepository.registerNewSalonAppointment(
                this@NewAppointmentActivity,
                appointment
            )
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
                binding.editServiceNameAppointment.setText(selectedService)
                binding.editServiceNameAppointment.isFocusable = true
            }
            .setNegativeButton("Cancelar") { _, _ ->
                binding.editServiceNameAppointment.isFocusable = true
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

                binding.editDateTime.setText(formattedDateTime)
                binding.tilDateTime.isErrorEnabled = false
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