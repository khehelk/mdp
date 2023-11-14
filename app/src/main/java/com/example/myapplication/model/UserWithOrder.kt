package com.example.myapplication.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithOrder(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "creatorUserId"
    )
    val orders: List<Order>
)