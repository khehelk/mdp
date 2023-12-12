package com.example.myapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.BasketWithServices
import com.example.myapplication.database.repository.BasketRepository
import com.example.myapplication.database.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BasketViewModel(private val basketRepository: BasketRepository, private val orderRepository: OrderRepository) : ViewModel() {
    private val _quantityStateMap = mutableMapOf<Int, MutableStateFlow<Int>>()
    private val _total = mutableDoubleStateOf(0.00)
    val total: State<Double> get() = _total
    fun getQuantityState(basketId: Int, serviceId: Int): StateFlow<Int> {
        val quantityStateFlow = _quantityStateMap.getOrPut(serviceId) {
            MutableStateFlow(0)
        }

        viewModelScope.launch {
            val quantityFromDb = basketRepository.getQuantity(basketId, serviceId)
            quantityFromDb?.let { quantityStateFlow.value = it }
        }

        return quantityStateFlow
    }

    suspend fun isServiceInBasket(basketId: Int, serviceId: Int): Boolean {
        return basketRepository.getService(basketId, serviceId) != null
    }

    fun addToBasket(basketServices: BasketService) = viewModelScope.launch {
        val isServiceInBasket = isServiceInBasket(basketServices.basketId, basketServices.serviceId)

        if (isServiceInBasket) {
            incrementQuantity(basketServices.basketId, basketServices.serviceId)
        } else {
            basketRepository.insertBasketService(basketServices)
        }
    }

    fun getBasketServices(id: Int): Flow<BasketWithServices> {
        return basketRepository.getBasketWithServices(id)
    }

    suspend fun getUsersBasket(id: Int): Basket {
        return basketRepository.getUsersBasket(id)
    }

    fun deleteServiceFromBasket(basketId: Int, serviceId: Int) = viewModelScope.launch {
        basketRepository.removeServiceFromBasket(basketId, serviceId)
    }

    fun incrementQuantity(basketId: Int, serviceId: Int) {
        val currentQuantity = _quantityStateMap[serviceId]?.value ?: 1
        _quantityStateMap[serviceId]?.value = currentQuantity + 1

        viewModelScope.launch {
            basketRepository.incrementServiceQuantity(basketId, serviceId)
            updateSubTotal(GlobalUser.getInstance().getUser()?.userId!!)
        }
    }

    fun decrementQuantity(basketId: Int, serviceId: Int) {
        val currentQuantity = _quantityStateMap[serviceId]?.value ?: 1
        if (currentQuantity > 1) {
            _quantityStateMap[serviceId]?.value = currentQuantity - 1

            viewModelScope.launch {
                basketRepository.decrementServiceQuantity(basketId, serviceId)
                updateSubTotal(GlobalUser.getInstance().getUser()?.userId!!)
            }
        }
    }

    fun updateSubTotal(userId: Int) {
        viewModelScope.launch {
            _total.value = getTotal(userId)
        }
    }

    suspend fun getTotal(userId: Int): Double {
        return basketRepository.getTotalPriceForBasket(basketRepository.getUsersBasket(userId!!).basketId!!) ?: 0.00
    }
}