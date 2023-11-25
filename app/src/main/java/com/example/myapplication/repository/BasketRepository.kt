package com.example.myapplication.repository

import com.example.myapplication.dao.BasketDao
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService

class BasketRepository(private val basketDao: BasketDao) {
    suspend fun insert(basket: Basket) = basketDao.insert(basket)
    suspend fun insertBasketService(basketService: BasketService) = basketDao.insertBasketService(basketService)
    suspend fun delete(basket: Basket) = basketDao.delete(basket)
    suspend fun getBasketWithServices(id: Int) = basketDao.getBasketWithServices(id)
    suspend fun getAllBaskets() = basketDao.getAllBaskets()
}