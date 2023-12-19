package com.example.myapplication.businessLogic.repository

import com.example.myapplication.api.model.UserRemoteSignIn
import com.example.myapplication.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    suspend fun authUser(user: UserRemoteSignIn): User
}