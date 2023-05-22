package com.nytech.embeauty.utils

import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.model.SalonServices
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Gera uma data no formato de string com ANO-MÊS-DIA
fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    return dateFormat.format(currentDate)
}

fun generateDummySalonServices() =
    SalonServices(
        services = listOf(
            SalonServices.Service(
                name = "Serviço 1",
                price = "R$ 50.00"
            ),
            SalonServices.Service(
                name = "Serviço 2",
                price = "R$ 70.00"
            ),
        )
    )

fun generateDummySalonAppointments() =
    SalonAppointments(
        appointments = listOf(
            SalonAppointments.Appointment(clientName = "Yan", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "09:00", endAt = "09:50", duration = "00:50", serviceName = "Serviço 1"),
            SalonAppointments.Appointment(clientName = "Nathália Miriam", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "10:00", endAt = "10:50", duration = "00:50", serviceName = "Serviço 2"),
            SalonAppointments.Appointment(clientName = "Nícolas Manoel", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "11:00", endAt = "11:50", duration = "00:50", serviceName = "Serviço 1"),
            SalonAppointments.Appointment(clientName = "Janete Gomes Maciel", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "12:00", endAt = "12:50", duration = "00:50", serviceName = "Serviço 2"),
            SalonAppointments.Appointment(clientName = "Luizinha", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "13:00", endAt = "13:50", duration = "00:50", serviceName = "Serviço 1"),
            SalonAppointments.Appointment(clientName = "Yago", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "14:00", endAt = "14:50", duration = "00:50", serviceName = "Serviço 2"),
            SalonAppointments.Appointment(clientName = "Teddy", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "15:00", endAt = "15:50", duration = "00:50", serviceName = "Serviço 1"),
            SalonAppointments.Appointment(clientName = "Silvana Alves", clientPhone = "5511966665555", date = getCurrentDate(), startAt = "16:00", endAt = "16:50", duration = "00:50", serviceName = "Serviço 2"),
        )
    )

