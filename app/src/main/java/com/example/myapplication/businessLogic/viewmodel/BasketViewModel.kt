package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.businessLogic.repository.OrderRepository
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ServiceWithQuantity(val service: Service, val quantityStateFlow: MutableStateFlow<Int>)

class BasketViewModel(private val basketRepository: BasketRepository, private val orderRepository: OrderRepository) : ViewModel() {
    private val _quantityMap = MutableStateFlow<Map<Int, MutableStateFlow<Int>>>(emptyMap())
    val quantityMap: StateFlow<Map<Int, MutableStateFlow<Int>>> get() = _quantityMap
    private val _total = mutableDoubleStateOf(0.00)
    val total: State<Double> get() = _total
    private val _myList = MutableStateFlow<List<Service>>(emptyList())
    val myList: StateFlow<List<Service>> get() = _myList
    private var _basketId = MutableStateFlow(0)
    val basketId: StateFlow<Int> get() = _basketId
    fun getQuantityState(basketId: Int, serviceId: Int): Flow<Int> {
        val quantityMap = _quantityMap.value.toMutableMap()
        val quantityStateFlow = quantityMap.getOrPut(serviceId) {
            MutableStateFlow(0)
        }

        viewModelScope.launch {
            val quantityFromDb = basketRepository.getQuantity(basketId, serviceId)
            quantityFromDb?.let { quantityStateFlow.value = it }
        }

        _quantityMap.value = quantityMap.toMap()

        return quantityStateFlow
    }

    suspend fun isServiceInBasket(basketId: Int, serviceId: Int): Boolean {
        return basketRepository.existService(basketId, serviceId)
    }

    suspend fun addToBasket(serviceId: Int, quantity: Int) = viewModelScope.launch {
        val basketId = GlobalUser.getInstance().getUser()?.basketId
        val isServiceInBasket = isServiceInBasket(basketId!!, serviceId)

        if (isServiceInBasket) {
            incrementServiceQuantity(basketId, serviceId)
        } else {
            basketRepository.insertBasketService(BasketService(basketId, serviceId, quantity))
        }
    }

    fun getBasketServices() {
        viewModelScope.launch {
            val basketId = GlobalUser.getInstance().getUser()?.basketId!!
            basketRepository.getBasketWithServices(basketId)
                .collect{services ->
                    _myList.value = services
                }
        }
    }

    fun getUsersBasket(id: Int) {
        viewModelScope.launch{
            val userBasketId = basketRepository.getUserBasketId(id).first()
            withContext(Dispatchers.Main) {
                _basketId.value = userBasketId
            }
        }
    }

    fun deleteServiceFromBasket(basketId: Int, serviceId: Int) {
        viewModelScope.launch {
            basketRepository.removeServiceFromBasket(basketId, serviceId)
            _quantityMap.value.toMutableMap().apply {
                remove(serviceId)
            }
            withContext(Dispatchers.Main){
                updateSubTotal(GlobalUser.getInstance().getUser()?.userId!!)
            }
        }
    }

    fun incrementServiceQuantity(basketId: Int, serviceId: Int) {
        viewModelScope.launch {
            basketRepository.incrementServiceQuantity(basketId, serviceId)
            val currentQuantity = getQuantityState(basketId, serviceId).first()
            _quantityMap.value.toMutableMap().apply {
                put(serviceId, MutableStateFlow(currentQuantity + 1))
            }
            updateSubTotal(GlobalUser.getInstance().getUser()?.userId!!)
        }
    }

    fun decrementOrRemoveServiceQuantity(basketId: Int, serviceId: Int) {
        viewModelScope.launch {
            val currentQuantity = getQuantityState(basketId, serviceId).first()
            if (currentQuantity > 1) {
                basketRepository.decrementServiceQuantity(basketId, serviceId)
                _quantityMap.value.toMutableMap().apply {
                    put(serviceId, MutableStateFlow(currentQuantity - 1))
                }
            }
            else{
                basketRepository.removeServiceFromBasket(basketId, serviceId)
                _quantityMap.value.toMutableMap().apply {
                    remove(serviceId, MutableStateFlow(currentQuantity - 1))
                }
                getBasketServices()
            }
            updateSubTotal(GlobalUser.getInstance().getUser()?.userId!!)
        }
    }

    fun updateSubTotal(userId: Int) {
        viewModelScope.launch {
            _total.doubleValue = getTotal(userId)
        }
    }

    suspend fun getTotal(userId: Int): Double {
        return basketRepository.getTotalPriceForUser(userId) ?: 0.00
    }
}