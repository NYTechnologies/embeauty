package com.nytech.embeauty.model

data class SalonServices(
    var services: List<Service> = emptyList()
) {

    data class Service(
        var name: String = "",
        var price: String = ""
    )
}