package com.example.myapplication.repository

import com.example.myapplication.dao.ServiceDao
import com.example.myapplication.model.Service

class ServiceRepository(private val serviceDao: ServiceDao) {
    suspend fun insert(service: Service) = serviceDao.insert(service)
    suspend fun update(service: Service) = serviceDao.update(service)
    suspend fun delete(service: Service) = serviceDao.delete(service)
    suspend fun getServiceById(id: Int) = serviceDao.getServiceById(id)
    suspend fun getAllServices() = serviceDao.getAllServices()
}