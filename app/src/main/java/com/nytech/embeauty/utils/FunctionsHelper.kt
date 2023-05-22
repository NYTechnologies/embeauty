package com.nytech.embeauty.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Gera uma data no formato de string com ANO-MÊS-DIA
fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    return dateFormat.format(currentDate)
}
