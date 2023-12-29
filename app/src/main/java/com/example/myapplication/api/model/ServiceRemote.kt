package com.example.myapplication.api.model

import com.example.myapplication.model.Service
import kotlinx.serialization.Serializable

@Serializable
data class ServiceRemote (
    val id: Int? = 0,
    val name: String = "",
    val price: Double = 0.0,
    val photo: String = ""
)

fun ServiceRemote.toService(): Service = Service(
    id,
    name,
    price,
    RemoteConverters.toBitmap(photo)
)

fun Service.toServiceRemote():ServiceRemote = ServiceRemote(
    serviceId,
    name,
    price,
    RemoteConverters.fromBitmap(photo)
)