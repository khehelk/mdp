package com.example.myapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.OrderWithServices
import com.example.myapplication.model.Service
import com.example.myapplication.model.UserWithOrder
import com.example.myapplication.database.repository.BasketRepository
import com.example.myapplication.database.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class OrderViewModel(private val orderRepository: OrderRepository, private val basketRepository: BasketRepository) : ViewModel() {
    private var _selectedItems = MutableLiveData<List<Service>>()
    val selectedItems get() = _selectedItems
    private val _total = mutableDoubleStateOf(0.00)
    val total: State<Double> get() = _total

    fun createOrder() = viewModelScope.launch {
        val userId = GlobalUser.getInstance().getUser()?.userId!!
        val order = Order(
            date = Date().time,
            total = getTotal(basketRepository.getUsersBasket(userId).basketId!!),
            creatorUserId = GlobalUser.getInstance().getUser()?.userId!!
        )

        var orderId = orderRepository.insert(order)

        for(service in selectedItems.value!!){
            val quantity = basketRepository.getQuantity(basketRepository.getUsersBasket(userId).basketId!!, service.serviceId!!)
            val orderService = OrderService( orderId.toInt(), service.serviceId!!, quantity!!)
            if (orderService != null) {
                orderRepository.insertOrderService(orderService)
            }
        }

        basketRepository.clearBasket(basketRepository.getUsersBasket(userId).basketId!!)
    }

    fun getOrderWithServices(id: Int) : Flow<OrderWithServices> {
        return orderRepository.getOrderWithServices(id)
    }

    fun getUserOrders(id: Int): Flow<UserWithOrder> {
        return orderRepository.getUserOrders(id)
    }

    fun updateSelectedItems(items: List<Service>) {
        _selectedItems.value = items
        val userId = GlobalUser.getInstance().getUser()?.userId!!
        updateSubTotal(userId)
    }

    fun updateSubTotal(userId: Int) {
        viewModelScope.launch {
            _total.value = getTotal(userId)
        }
    }

    suspend fun getTotal(basketId: Int): Double {
        return basketRepository.getTotalPriceForBasket(basketId) ?: 0.00
    }
}