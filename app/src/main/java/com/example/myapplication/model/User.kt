package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    val role: RoleEnum,
    val photo: Int? = null,
    val basketId: Int? = null
)