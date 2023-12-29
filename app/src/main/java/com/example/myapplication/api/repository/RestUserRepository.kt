package com.example.myapplication.api.repository

import com.example.myapplication.api.ServerService
import com.example.myapplication.api.model.UserRemoteSignIn
import com.example.myapplication.api.model.toUser
import com.example.myapplication.api.model.toUserRemote
import com.example.myapplication.businessLogic.repository.UserRepository
import com.example.myapplication.model.User

class RestUserRepository(
    private var service: ServerService
): UserRepository {
    override suspend fun insert(user: User) {
        service.SignUp(user.toUserRemote())
    }

    override suspend fun update(user: User) {
        service.updateUser(user.toUserRemote())
    }

    override suspend fun delete(user: User) {
        println()
    }
    override suspend fun authUser(user: UserRemoteSignIn): User {
        return service.SignIn(user).toUser()
    }
}