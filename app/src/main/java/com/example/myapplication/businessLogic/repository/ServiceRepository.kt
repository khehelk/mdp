package com.example.myapplication.businessLogic.repository

import androidx.paging.PagingData
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun insert(service: Service)
    suspend fun update(service: Service)
    suspend fun delete(service: Service)
    suspend fun getServiceById(id: Int): Service
    suspend fun getAllServices(): Flow<PagingData<Service>>
}