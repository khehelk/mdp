package com.example.myapplication.api.repository

import com.example.myapplication.api.ServerService
import com.example.myapplication.model.User
import com.example.myapplication.database.repository.UserRepository

class RestUserRepository(
    private var server: ServerService
): UserRepository {
    override suspend fun createUser(user: User){
        server.SignUp(user.toUserRemote())
    }
}