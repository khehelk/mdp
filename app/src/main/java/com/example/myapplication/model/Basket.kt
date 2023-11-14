package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_basket")
data class Basket(
    @PrimaryKey(autoGenerate = true)
    val basketId: Int? = null,
    val date: Long,
    val total: Double,
    val creatorUserId: Int
)