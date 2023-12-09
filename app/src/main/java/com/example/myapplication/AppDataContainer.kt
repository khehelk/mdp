package com.example.myapplication

import android.content.Context
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.repository.BasketRepository
import com.example.myapplication.repository.OrderRepository
import com.example.myapplication.repository.ServiceRepository
import com.example.myapplication.repository.UserRepository

class AppDataContainer(context: Context) : AppContainer {
    override val serviceRepo = ServiceRepository(AppDatabase.getInstance(context).serviceDao())
    override val userRepo = UserRepository(AppDatabase.getInstance(context).userDao())
    override val orderRepo = OrderRepository(AppDatabase.getInstance(context).orderDao())
    override val basketRepo = BasketRepository(AppDatabase.getInstance(context).basketDao())
}