package com.example.myapplication.repository

import com.example.myapplication.dao.UserDao
import com.example.myapplication.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(user: User) = userDao.delete(user)
    suspend fun getUserById(id: Int) = userDao.getUserById(id)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun getUserByEmail(id: Int) = userDao.getUserOrders(id)
}