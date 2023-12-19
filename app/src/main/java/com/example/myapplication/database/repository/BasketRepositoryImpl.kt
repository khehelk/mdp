package com.example.myapplication.database.repository

import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.database.dao.BasketDao
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class BasketRepositoryImpl(private val basketDao: BasketDao): BasketRepository {
    suspend fun createBasket(basket: Basket): Long = basketDao.insert(basket)
    suspend fun getUsersBasket(userId: Int): Basket = basketDao.getUsersBasket(userId)
    override suspend fun removeServiceFromBasket(basketId: Int, serviceId: Int) = basketDao.removeServiceFromBasket(basketId, serviceId)
    suspend fun updateServiceQuantity(basketId: Int, serviceId: Int, quantity: Int) = basketDao.updateServiceQuantity(basketId, serviceId, quantity)
    override suspend fun incrementServiceQuantity(basketId: Int, serviceId: Int) = basketDao.incrementServiceQuantity(basketId, serviceId)
    override suspend fun decrementServiceQuantity(basketId: Int, serviceId: Int) = basketDao.decrementServiceQuantity(basketId, serviceId)
    override suspend fun insertBasketService(basketService: BasketService) = basketDao.insertBasketService(basketService)
    override suspend fun getBasketWithServices(id: Int): Flow<List<Service>> = flow {basketDao.getBasketWithServices(id).first().services}
    override suspend fun getUserBasketId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }
    suspend fun delete(basket: Basket) = basketDao.delete(basket)
    override suspend fun getQuantity(basketId: Int, serviceId: Int): Int? = basketDao.getQuantity(basketId, serviceId)
    override suspend fun existService(basketId: Int, serviceId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalPriceForUser(userId: Int): Double? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllServiceFromBasket(basketId: Int) {
        TODO("Not yet implemented")
    }

    suspend fun getService(basketId: Int, serviceId: Int): BasketService? = basketDao.getService(basketId, serviceId)
    suspend fun getTotalPriceForBasket(basketId: Int): Double? = basketDao.getTotalPriceForBasket(basketId)
    suspend fun clearBasket(basketId: Int) = basketDao.clearBasket(basketId)
}