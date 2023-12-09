package com.example.myapplication.model

import androidx.room.Entity

@Entity(tableName = "tbl_basket_service",
        primaryKeys = ["basketId", "serviceId"])
data class BasketService(
    val basketId: Int,
    val serviceId: Int,
    val quantity: Int
)