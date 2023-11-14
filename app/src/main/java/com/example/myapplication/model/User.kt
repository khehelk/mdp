package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: RoleEnum,
    val photo: Int? = null
)