package com.nytech.embeauty.model

// Objeto/modelo a ser enviado para o banco de dados "Salon" no FireBase Store
data class SalonModel(
    val firebaseUID: String,
    var name: String,
    var phone: String,
    var email: String
)