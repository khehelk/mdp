package com.example.myapplication.model

import androidx.room.Entity

@Entity(primaryKeys = ["orderId", "serviceId"], tableName = "tbl_order_service")
data class OrderService(
    val orderId: Int,
    val serviceId: Int,
    val quantity: Int
)