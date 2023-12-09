package com.example.myapplication

import com.example.myapplication.repository.BasketRepository
import com.example.myapplication.repository.OrderRepository
import com.example.myapplication.repository.ServiceRepository
import com.example.myapplication.repository.UserRepository

interface AppContainer {
    val serviceRepo: ServiceRepository
    val userRepo: UserRepository
    val orderRepo: OrderRepository
    val basketRepo: BasketRepository
}