package com.example.myapplication.repository

import com.example.myapplication.dao.BasketDao
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.BasketWithServices
import kotlinx.coroutines.flow.Flow

class BasketRepository(private val basketDao: BasketDao) {
    suspend fun createBasket(basket: Basket): Long = basketDao.insert(basket)
    suspend fun getUsersBasket(userId: Int): Basket = basketDao.getUsersBasket(userId)
    suspend fun removeServiceFromBasket(basketId: Int, serviceId: Int) = basketDao.removeServiceFromBasket(basketId, serviceId)
    suspend fun updateServiceQuantity(basketId: Int, serviceId: Int, quantity: Int) = basketDao.updateServiceQuantity(basketId, serviceId, quantity)
    suspend fun incrementServiceQuantity(basketId: Int, serviceId: Int) = basketDao.incrementServiceQuantity(basketId, serviceId)
    suspend fun decrementServiceQuantity(basketId: Int, serviceId: Int) = basketDao.decrementServiceQuantity(basketId, serviceId)
    suspend fun insertBasketService(basketService: BasketService) = basketDao.insertBasketService(basketService)
    fun getBasketWithServices(id: Int): Flow<BasketWithServices> = basketDao.getBasketWithServices(id)
    fun getAllBasket(): Flow<List<Basket>> = basketDao.getAllBaskets()
    suspend fun delete(basket: Basket) = basketDao.delete(basket)
    suspend fun getQuantity(basketId: Int, serviceId: Int): Int? = basketDao.getQuantity(basketId, serviceId)
    suspend fun getService(basketId: Int, serviceId: Int): BasketService? = basketDao.getService(basketId, serviceId)
    suspend fun getTotalPriceForBasket(basketId: Int): Double? = basketDao.getTotalPriceForBasket(basketId)
    suspend fun clearBasket(basketId: Int) = basketDao.clearBasket(basketId)
}