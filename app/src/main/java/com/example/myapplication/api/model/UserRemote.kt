package com.example.myapplication.api.model

import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRemote (
    val id: Int? = 0,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val role: RoleEnum = RoleEnum.User,
    val photo: Int? = 0,
    val basketId: Int? = 0
)


fun UserRemote.toUser(): User = User(
    id,
    name,
    surname,
    email,
    password,
    role,
    photo,
    basketId
)

fun User.toUserRemote():UserRemote = UserRemote(
    userId,
    name,
    surname,
    email,
    password,
    role,
    photo,
    basketId
)