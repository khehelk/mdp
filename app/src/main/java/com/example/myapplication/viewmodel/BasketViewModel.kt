package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.BasketWithServices
import com.example.myapplication.model.Service
import com.example.myapplication.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BasketViewModel(private val basketRepository: BasketRepository) : ViewModel() {
    val date = mutableLongStateOf(0L)
    private var _selectedItems = mutableStateOf<List<Service>>(emptyList())
    val selectedItems get() = _selectedItems.value

    fun createBasket() = viewModelScope.launch {
        val basket = Basket(
            date = date.value,
            total = getTotal(),
            creatorUserId = GlobalUser.getInstance().getUser()?.userId!!
        )

        var basketId = basketRepository.insert(basket)

        for(service in selectedItems){
            val basketService = BasketService(null, basketId.toInt(), service.serviceId!!)
            basketRepository.insertBasketService(basketService)
        }
    }

    suspend fun getBasketWithServices(id: Int) : Flow<BasketWithServices> {
        return basketRepository.getBasketWithServices(id)
    }

    private fun getTotal(): Double {
        return selectedItems.sumOf { it.price }
    }
}