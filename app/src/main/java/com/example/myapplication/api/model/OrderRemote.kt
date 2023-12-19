package com.example.myapplication.api.model

import com.example.myapplication.model.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderRemote(
    val id: Int? = 0,
    val date: Long = 0L,
    val total: Double = 0.0,
    val creatorUserId: Int = 0
)

fun OrderRemote.toOrder(): Order = Order(
    id,
    date,
    total,
    creatorUserId
)

fun Order.toOrderRemote():OrderRemote = OrderRemote(
    orderId,
    date,
    total,
    creatorUserId
)