package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.businessLogic.repository.OrderRepository
import com.example.myapplication.model.Order
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.Date

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val basketRepository: BasketRepository
) : MyViewModel() {
    private var _selectedItems = MutableLiveData<List<Service>>()
    val selectedItems get() = _selectedItems
    private val _total = mutableDoubleStateOf(0.00)
    val total: State<Double> get() = _total
    private var _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders
    fun createOrder(): Boolean {
        viewModelScope.launch {
            val userId = GlobalUser.getInstance().getUser()?.userId!!
            val order = Order(
                date = Date().time,
                total = getTotal(userId),
                creatorUserId = userId
            )
            runInScope(
                actionSuccess = { orderRepository.insert(order) }
            )
        }
        return true
    }

    suspend fun getOrderWithServices(id: Int) : Flow<List<Service>> {
        return try{
            orderRepository.getServiceFromOrder(id)
        }catch (e: Exception){
            emptyFlow()
        }
    }

    suspend fun getUserOrders(id: Int) {
        runInScope(
            actionSuccess = {
                viewModelScope.launch {
                    orderRepository.getUserOrders(id)
                        .collect{items ->
                            _orders.value = items
                        }
                }
            }
        )
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
        return basketRepository.getTotalPriceForUser(basketId) ?: 0.00
    }
}