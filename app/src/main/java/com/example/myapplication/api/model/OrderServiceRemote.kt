package com.example.myapplication.api.model

import com.example.myapplication.model.OrderService
import kotlinx.serialization.Serializable

@Serializable
data class OrderServiceRemote (
    val orderId: Int = 0,
    val serviceId: Int = 0,
    val quantity: Int = 0
)

fun OrderServiceRemote.toOrderService(): OrderService = OrderService(
    orderId,
    serviceId,
    quantity
)

fun OrderService.toOrderServiceRemote():OrderServiceRemote = OrderServiceRemote(
    orderId,
    serviceId,
    quantity
)