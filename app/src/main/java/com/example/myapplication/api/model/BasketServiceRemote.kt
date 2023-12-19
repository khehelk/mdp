package com.example.myapplication.api.model

import com.example.myapplication.model.BasketService
import kotlinx.serialization.Serializable

@Serializable
data class BasketServiceRemote (
    val basketId: Int = 0,
    val serviceId: Int = 0,
    val quantity: Int = 0
)

fun BasketServiceRemote.toBasketService(): BasketService = BasketService(
    basketId,
    serviceId,
    quantity
)

fun BasketService.toBasketServiceRemote():BasketServiceRemote = BasketServiceRemote(
    basketId,
    serviceId,
    quantity
)