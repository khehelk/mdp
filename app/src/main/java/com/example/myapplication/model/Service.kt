package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_service")
data class Service (
    @PrimaryKey(autoGenerate = true)
    val serviceId: Int? = null,
    val name: String,
    val price: Double,
    val photo: Int? = null
)