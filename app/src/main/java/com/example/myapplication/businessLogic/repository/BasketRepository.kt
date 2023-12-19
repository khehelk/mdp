package com.example.myapplication.businessLogic.repository

import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun insertBasketService(basketService: BasketService)
    suspend fun getBasketWithServices(id: Int): Flow<List<Service>>
    suspend fun getUserBasketId(id: Int): Flow<Int>
    suspend fun removeServiceFromBasket(basketId: Int, serviceId: Int)
    suspend fun incrementServiceQuantity(basketId: Int, serviceId: Int)
    suspend fun decrementServiceQuantity(basketId: Int, serviceId: Int)
    suspend fun getQuantity(basketId: Int, serviceId: Int): Int?
    suspend fun existService(basketId: Int, serviceId: Int): Boolean
    suspend fun getTotalPriceForUser(userId: Int): Double?
    suspend fun deleteAllServiceFromBasket(basketId: Int)
}