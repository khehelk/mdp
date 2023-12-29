package com.example.myapplication.businessLogic.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.businessLogic.repository.ServiceRepository
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val serviceRepository: ServiceRepository
): MyViewModel() {
    var name = mutableStateOf("")
    var price = mutableDoubleStateOf(0.00)
    private val _serviceList = MutableStateFlow<Flow<PagingData<Service>>>(emptyFlow())
    val serviceList: StateFlow<Flow<PagingData<Service>>> get() = _serviceList

    init {
        runInScope(
            actionSuccess = { getServiceList() }
        )
    }

    fun insertService(photo: Bitmap) {
        val service = Service(
            name = name.value,
            price = price.doubleValue,
            photo = photo
        )
        runInScope(
            actionSuccess = { serviceRepository.insert(service) }
        )
    }

    fun deleteService(service :  Service) {
        runInScope(
            actionSuccess = {
                serviceRepository.delete(service)
                getServiceList()
            }
        )
    }

    fun updateService(service: Service) {
        runInScope(
            actionSuccess = {
                serviceRepository.update(service)
                getServiceList()
            }
        )
    }

    fun getServiceList(){
        try{
            viewModelScope.launch{
                _serviceList.value = serviceRepository.getAllServices()
            }
        }catch(e: Exception){}

    }

    fun searchServicesByFilter(searchText: String){
        viewModelScope.launch {
            val filteredServices = serviceRepository.call(searchText).cachedIn(viewModelScope)
            _serviceList.value = filteredServices
        }
    }
}