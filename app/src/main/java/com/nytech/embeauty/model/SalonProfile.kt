package com.nytech.embeauty.model

//representa os dados que constam no Firestore Database, na collection SalonProfile
data class SalonProfile(
    val firebaseUID: String = "",
    var name: String = "",
    var phone: String = "",
    var email: String = "",
)