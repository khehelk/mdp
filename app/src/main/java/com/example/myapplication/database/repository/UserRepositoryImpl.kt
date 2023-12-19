package com.example.myapplication.database.repository

import com.example.myapplication.api.model.UserRemoteSignIn
import com.example.myapplication.businessLogic.repository.UserRepository
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.model.User

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun insert(user: User) = userDao.insert(user)
    override suspend fun update(user: User) = userDao.update(user)
    override suspend fun delete(user: User) = userDao.delete(user)
    override suspend fun authUser(user: UserRemoteSignIn): User {
        TODO("Not yet implemented")
    }
    suspend fun getUserById(id: Int) = userDao.getUserById(id)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
}