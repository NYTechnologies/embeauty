package com.nytech.embeauty.model

data class SalonAppointments(
    var appointment: List<Appointment> = emptyList()
) {
    data class Appointment(
        var clientName: String = "",
        var clientPhone: String = "",
        var date: String = "",
        var startAt: String = "",
        var endAt: String = "",
        var duration: String = "",
        var serviceName: String = ""
    )
}