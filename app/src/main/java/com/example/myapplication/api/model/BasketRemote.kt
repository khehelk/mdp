package com.example.myapplication.api.model

import kotlinx.serialization.Serializable

@Serializable
data class BasketRemote (
    val id: Int? = 0,
    val userId: Int = 0,
)