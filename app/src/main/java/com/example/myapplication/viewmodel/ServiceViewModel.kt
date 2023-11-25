package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.model.Service
import com.example.myapplication.repository.ServiceRepository
import kotlinx.coroutines.launch

class ServiceViewModel(private val serviceRepository: ServiceRepository): ViewModel() {
    var name = mutableStateOf("")
    var price = mutableDoubleStateOf(0.00)
    var photo = mutableIntStateOf(R.drawable.image_service)
    var service: Service? = null

    fun createService() = viewModelScope.launch {
        val service = Service(
            name = name.value,
            price = price.doubleValue,
            photo = photo.intValue
        )
        serviceRepository.insert(service)
    }

    fun updateService(service: Service) = viewModelScope.launch {
        serviceRepository.update(service)
    }

    fun deleteService(service: Service) = viewModelScope.launch {
        serviceRepository.delete(service)
    }

    fun getServiceById(id: Int) = viewModelScope.launch {
        serviceRepository.getServiceById(id)
    }

    fun getAllServices() = viewModelScope.launch {
        serviceRepository.getAllServices()
    }
}