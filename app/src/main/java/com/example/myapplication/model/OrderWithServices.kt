package com.example.myapplication.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OrderWithServices(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "serviceId",
        associateBy = Junction(OrderService::class)
    )
    val services: List<Service>
)