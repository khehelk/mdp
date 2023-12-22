package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.myapplication.R
import com.example.myapplication.businessLogic.repository.ServiceRepository
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceViewModel(private val serviceRepository: ServiceRepository): ViewModel() {
    var name = mutableStateOf("")
    var price = mutableDoubleStateOf(0.00)
    var photo = mutableIntStateOf(R.drawable.image_service)
    var service = mutableStateOf<Service>(Service(null,"", 0.0, null))
    private val _serviceList = MutableStateFlow<PagingData<Service>>(PagingData.empty())
    val serviceList: StateFlow<PagingData<Service>> get() = _serviceList
    fun insertService() = viewModelScope.launch {
        val service = Service(
            name = name.value,
            price = price.doubleValue,
            photo = photo.intValue
        )
        serviceRepository.insert(service)
    }

    fun deleteService(service :  Service) = viewModelScope.launch {
        serviceRepository.delete(service)
    }

    fun updateService() = viewModelScope.launch {
        serviceRepository.update(service.value)
    }

    fun getServiceList(){
        viewModelScope.launch {
            serviceRepository.getAllServices()
                .collect{services ->
                    _serviceList.value = services
                }
        }
    }
}