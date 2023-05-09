package com.nytech.embeauty.model

// Objeto/modelo a ser enviado para o banco de dados "Salon" no FireBase Store
data class SalonModel(
    val firebaseUID: String = "",
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var services: List<Service> = generateGenericServices(),
    var appointments: List<Appointment> = emptyList()
) {

    data class Service(
        var name: String = "",
        var duration: String = "",
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
}

// Função para gerar serviços genéricos
private fun generateGenericServices() = listOf(
    SalonModel.Service("Serviço 1", "30 min", "R$ 20,00"),
    SalonModel.Service("Serviço 2", "60 min", "R$ 50,00")
)