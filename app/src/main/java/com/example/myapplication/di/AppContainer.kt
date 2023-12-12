package com.example.myapplication.di

import com.example.myapplication.database.repository.BasketRepository
import com.example.myapplication.database.repository.OrderRepository
import com.example.myapplication.database.repository.ServiceRepository
import com.example.myapplication.database.repository.UserRepository

interface AppContainer {
    val serviceRepo: ServiceRepository
    val userRepo: UserRepository
    val orderRepo: OrderRepository
    val basketRepo: BasketRepository

    companion object {
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}