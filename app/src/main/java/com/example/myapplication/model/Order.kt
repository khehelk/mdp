package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int? = null,
    val date: Long,
    val total: Double,
    val creatorUserId: Int
)