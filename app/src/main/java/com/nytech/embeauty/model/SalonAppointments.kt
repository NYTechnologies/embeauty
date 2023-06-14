package com.nytech.embeauty.model

import com.google.firebase.Timestamp
import com.nytech.embeauty.constants.GenericConstants
import java.text.SimpleDateFormat
import java.util.*

data class SalonAppointments(
    var appointments: List<Appointment> = emptyList()
) {
    data class Appointment(
        var uuid: String = UUID.randomUUID().toString(),
        var clientName: String = "",
        var clientPhone: String = "",
        var startDateTime: String = "",
        var durationMinutes: Int = 0,
        var serviceName: String = ""
    ) {
        var startTimestamp: Timestamp? = null
        var endDateTime: String = ""
        var endTimestamp: Timestamp? = null

        fun setsStartTimestamp(startDateTime: String) {
            val sdfDateTime = SimpleDateFormat(GenericConstants.BR_DATETIME_FORMAT, Locale.getDefault())
            val date = sdfDateTime.parse(startDateTime)
            startTimestamp = Timestamp(date!!)
        }
        fun setEndParameters(startTimestamp: Timestamp, durationMinutes: Int) {
            val calendar = Calendar.getInstance()
            calendar.time = startTimestamp.toDate()

            // Adicionar a duração em minutos ao horário inicial
            calendar.add(Calendar.MINUTE, durationMinutes)

            // Dar valor ao endTimestamp do construtor com o Timestamp final calculado
            endTimestamp = Timestamp(calendar.time)

            // Formatar a data e hora final como string
            val sdfDateTime = SimpleDateFormat(GenericConstants.BR_DATETIME_FORMAT, Locale.getDefault())
            // Atribuir o formato de Data e Hora nas propriedades do construtor
            endDateTime = sdfDateTime.format(calendar.time)
        }
    }
}