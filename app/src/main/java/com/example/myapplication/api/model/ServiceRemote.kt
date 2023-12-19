package com.example.myapplication.api.model

import com.example.myapplication.model.Service
import kotlinx.serialization.Serializable

@Serializable
data class ServiceRemote (
    val id: Int? = 0,
    val name: String = "",
    val price: Double = 0.0,
    val photo: Int? = 0
)

fun ServiceRemote.toService(): Service = Service(
    id,
    name,
    price,
    photo
)

fun Service.toServiceRemote():ServiceRemote = ServiceRemote(
    serviceId,
    name,
    price,
    photo
)