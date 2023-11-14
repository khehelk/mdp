package com.example.myapplication.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BasketWithServices(
    @Embedded val basket: Basket,
    @Relation(
        parentColumn = "basketId",
        entityColumn = "serviceId",
        associateBy = Junction(BasketService::class)
    )
    val services: List<Service>
)