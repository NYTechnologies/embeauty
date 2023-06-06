package com.nytech.embeauty.utils

import com.nytech.embeauty.constants.GenericConstants
import com.nytech.embeauty.model.SalonAppointments
import com.nytech.embeauty.model.SalonServices
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Gera uma data no formato de string com ANO-MÊS-DIA
fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern(GenericConstants.BR_DATE_FORMAT)
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
