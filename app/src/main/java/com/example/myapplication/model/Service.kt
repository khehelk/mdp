package com.example.myapplication.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_service")
data class Service (
    @PrimaryKey(autoGenerate = true)
    var serviceId: Int? = null,
    var name: String,
    var price: Double,
    var photo: Bitmap
)