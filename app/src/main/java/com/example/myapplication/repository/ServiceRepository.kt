package com.example.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.dao.ServiceDao
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

class ServiceRepository(private val serviceDao: ServiceDao) {
    suspend fun insert(service: Service) = serviceDao.insert(service)
    suspend fun update(service: Service) = serviceDao.update(service)
    suspend fun delete(service: Service) = serviceDao.delete(service)
    suspend fun getServiceById(id: Int) = serviceDao.getServiceById(id)
    fun getAllServices() = serviceDao.getAllServices()
    fun call(): Flow<PagingData<Service>> {
        return Pager(
            PagingConfig(pageSize = 5)
        ){
            serviceDao.getAllServicesPaged()
        }.flow
    }
}