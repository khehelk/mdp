package com.example.myapplication.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ServiceWithCount(
    val service: ServiceRemote,
    val quantity: Int
)