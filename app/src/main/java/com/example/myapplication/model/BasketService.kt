package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_basket_service")
data class BasketService(
    @PrimaryKey(autoGenerate = true)
    val basketServiceId: Int? = null,
    val basketId: Int,
    val serviceId: Int,
)