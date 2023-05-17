package com.nytech.embeauty.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        var endAt: String = "",
        var duration: String = "",
        var serviceName: String = ""
    )

    fun todayAppointments(): List<Appointment> = appointments.filter { it.date == getCurrentDate() }
}

// Função para gerar serviços genéricos
private fun generateGenericServices() = listOf(
    SalonModel.Service("Serviço 1", "R$ 20.00"),
    SalonModel.Service("Serviço 2", "R$ 50.00")
)

private fun generateGenericAppointments() = listOf(
    SalonModel.Appointment(clientName = "Yan", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Nat", date = getCurrentDate()),
    SalonModel.Appointment(clientName = "Nada a ver", date = "2030-05-20")
)

// Gera uma data no formato de string com ANO-MÊS-DIA
fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    return dateFormat.format(currentDate)
}
