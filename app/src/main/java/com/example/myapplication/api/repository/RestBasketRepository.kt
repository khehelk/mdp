package com.example.myapplication.api.repository

import com.example.myapplication.api.ServerService
import com.example.myapplication.api.model.toBasketServiceRemote
import com.example.myapplication.api.model.toService
import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestBasketRepository(
    private var service: ServerService
): BasketRepository {
    override suspend fun insertBasketService(basketService: BasketService) {
        service.addServiceToBasket(basketService.toBasketServiceRemote())
    }

    override suspend fun getBasketWithServices(id: Int): Flow<List<Service>> {
        val servicesRemoteList = service.getUserBasketServices(id)
        val servicesList = servicesRemoteList.map { it.toService() }
        return flowOf(servicesList.toList())
    }

    override suspend fun getUserBasketId(id: Int): Flow<Int> {
        return flowOf(service.getUserBasket(id))
    }

    override suspend fun getQuantity(basketId: Int, serviceId: Int): Int? {
        return service.getQuantity(basketId, serviceId)
    }
    //
//    override fun getAllBasket(): Flow<List<Basket>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun delete(basket: Basket) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun createBasket(basket: Basket): Long {
//        TODO("Not yet implemented")
//    }
//
    override suspend fun removeServiceFromBasket(basketId: Int, serviceId: Int) {
        service.deleteServiceFromBasket(basketId, serviceId)
    }
    //
//    override suspend fun updateServiceQuantity(basketId: Int, serviceId: Int, quantity: Int) {
//        TODO("Not yet implemented")
//    }
//
    override suspend fun incrementServiceQuantity(basketId: Int, serviceId: Int) {
        service.increment(basketId, serviceId)
    }

    override suspend fun decrementServiceQuantity(basketId: Int, serviceId: Int) {
        service.decrement(basketId, serviceId)
    }
    //
//    override suspend fun getQuantity(basketId: Int, serviceId: Int): Int? {
//        TODO("Not yet implemented")
//    }
//
    override suspend fun existService(basketId: Int, serviceId: Int): Boolean {
        return service.getService(basketId, serviceId)
    }

    override suspend fun getTotalPriceForUser(userId: Int): Double? {
        return service.getTotalPriceForUserBasket(userId)
    }

    override suspend fun deleteAllServiceFromBasket(basketId: Int) {
        service.deleteAllServiceFromBasket(basketId)
    }
}