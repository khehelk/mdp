package com.example.myapplication.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteSignIn(
    val email: String = "",
    val password: String = "",
)