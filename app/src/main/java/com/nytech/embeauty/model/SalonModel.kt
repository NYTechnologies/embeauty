package com.nytech.embeauty.model

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

// Objeto/modelo a ser enviado para o banco de dados "Salon" no FireBase Store
data class SalonModel(
    val firebaseUID: String = "",
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var services: List<Service> = generateGenericServices(),
    var appointments: List<Appointment> = generateGenericAppointments()
) {

    data class Service(
        var name: String = "",
        var price: String = ""
    )

    data class Appointment(
        var clientName: String = "",
        var clientPhone: String = "",
        var date: String = "",
        var startAt: String = "",
        var duration: String = "",
        var serviceName: String = ""
    ) {
        var endAt: String = ""

        // Inicializa o objeto calculando o endAt com base no startAt e no duration
        init {
            val startAtTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(startAt)
            val durationTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(duration)

            val calendar = Calendar.getInstance().apply { time = startAtTime!! }

            calendar.add(Calendar.HOUR_OF_DAY, durationTime!!.hours)
            calendar.add(Calendar.MINUTE, durationTime.minutes)

            val endAtTime = calendar.time

            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            endAt = formatter.format(endAtTime)
        }
    }

    // Função que retorna os agendamentos de hoje
    fun todayAppointments(): List<Appointment> = appointments.filter { it.date == getCurrentDate() }
}

// Função para gerar serviços genéricos
private fun generateGenericServices() = listOf(
    SalonModel.Service("Unha Mão", "R$ 20.00"),
    SalonModel.Service("Unha Pé", "R$ 50.00"),
    SalonModel.Service("Depilação", "R$ 50.00"),
    SalonModel.Service("Corte Simples", "R$ 50.00"),
    SalonModel.Service("Progressiva", "R$ 50.00"),
    SalonModel.Service("Sombrancelha", "R$ 50.00"),
    SalonModel.Service("Pedicure", "R$ 50.00"),
    SalonModel.Service("Cílios", "R$ 50.00"),
    SalonModel.Service("Unha Mão", "R$ 20.00"),
    SalonModel.Service("Unha Pé", "R$ 50.00"),
    SalonModel.Service("Depilação", "R$ 50.00"),
    SalonModel.Service("Corte Simples", "R$ 50.00"),
    SalonModel.Service("Progressiva", "R$ 50.00"),
    SalonModel.Service("Sombrancelha", "R$ 50.00"),
    SalonModel.Service("Cílios", "R$ 50.00"),
)

private fun generateGenericAppointments() = listOf(
    SalonModel.Appointment(clientName = "Yan", startAt = "09:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Nat", startAt = "10:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Nícolas", startAt = "11:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Silvana Alves", startAt = "12:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Janete Maciel", startAt = "14:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Luizinha Maciel", startAt = "15:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Gabriela Gomes Maciel", startAt = "16:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Yago", startAt = "17:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Manoel da Silva", startAt = "18:00", duration = "00:50", serviceName = "Unha da Mão", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Nada a ver", date = "2030-05-20")
)

// Gera uma data no formato de string com ANO-MÊS-DIA
fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    return dateFormat.format(currentDate)
}
