package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.api.ServerService
import com.example.myapplication.api.repository.RestBasketRepository
import com.example.myapplication.api.repository.RestOrderRepository
import com.example.myapplication.api.repository.RestServiceRepository
import com.example.myapplication.api.repository.RestUserRepository
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.repository.BasketRepository
import com.example.myapplication.database.repository.OrderRepository
import com.example.myapplication.database.repository.ServiceRepository
import com.example.myapplication.database.repository.UserRepository

class AppDataContainer(context: Context) : AppContainer {
    override val serviceRepo = ServiceRepository by lazy {
        RestServiceRepository(
            ServerService.getInstance(),
            serviceRepository,
            AppDatabase.getInstance(context),
            remoteKeyRepository
        )
    }
    override val userRepo = UserRepository by lazy{
        RestUserRepository(ServerService.getInstance())
    }
    override val orderRepo = OrderRepository by lazy {
        RestOrderRepository(ServerService.getInstance())
    }
    override val basketRepo = BasketRepository by lazy{
        RestBasketRepository(ServerService.getInstance())
    }

    private val serviceRepository: ServiceRepository by lazy {
        ServiceRepository(AppDatabase.getInstance(context).serviceDao())
    }
    private val remoteKeyRepository: RemoteKeysRepository by lazy{
        RemoteKeysRepository(AppDatabase.getInstance(context).remoteKeysDao())
    }
}