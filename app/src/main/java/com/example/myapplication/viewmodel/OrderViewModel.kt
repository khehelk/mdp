package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.OrderWithServices
import com.example.myapplication.model.Service
import com.example.myapplication.model.UserWithOrder
import com.example.myapplication.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    private var _selectedItems = mutableStateOf<List<Service>>(emptyList())
    val selectedItems get() = _selectedItems.value

    fun createOrder() = viewModelScope.launch {
        val order = Order(
            date = Date().time,
            total = getTotal(),
            creatorUserId = GlobalUser.getInstance().getUser()?.userId!!
        )

        var orderId = orderRepository.insert(order)

        for(service in selectedItems){
            val orderService = OrderService(orderId.toInt(), service.serviceId!!)
            orderRepository.insertOrderService(orderService)
        }
    }

    suspend fun getOrderWithServices(id: Int) : Flow<OrderWithServices> {
        return orderRepository.getOrderWithServices(id)
    }

    suspend fun getUserOrders(id: Int): Flow<UserWithOrder> {
        return orderRepository.getUserOrders(id)
    }

    private fun getTotal(): Double {
        return selectedItems.sumOf { it.price }
    }
}