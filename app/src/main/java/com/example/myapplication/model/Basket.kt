package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_basket")
data class Basket(
    @PrimaryKey
    val basketId: Int? = null,
    val creatorUserId: Int
)