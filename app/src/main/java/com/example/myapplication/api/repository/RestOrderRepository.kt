package com.example.myapplication.api.repository

import com.example.myapplication.api.ServerService
import com.example.myapplication.api.model.toOrder
import com.example.myapplication.api.model.toOrderRemote
import com.example.myapplication.api.model.toOrderServiceRemote
import com.example.myapplication.api.model.toService
import com.example.myapplication.businessLogic.repository.OrderRepository
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestOrderRepository(private val server: ServerService): OrderRepository {
    override suspend fun insert(order: Order): Long {
        return server.createOrder(order.toOrderRemote())
    }

    override suspend fun addServiceToOrder(orderService: OrderService) {
        server.addServiceToOrder(orderService.toOrderServiceRemote())
    }

    override suspend fun delete(order: Order) {
        order.orderId?.let { this.server.deleteOrder(it) }
    }

    override suspend fun getServiceFromOrder(id: Int): Flow<List<Service>> {
        val servicesRemoteList = server.getServiceFromOrder(id)
        val servicesList = servicesRemoteList.map { it.toService() }
        return flowOf(servicesList.toList())
    }

    override suspend fun getUserOrders(id: Int): Flow<List<Order>> {
        val ordersRemoteList = server.getUserOrders(id)
        val ordersList = ordersRemoteList.map { it.toOrder() }
        return flowOf(ordersList.toList())
    }
}