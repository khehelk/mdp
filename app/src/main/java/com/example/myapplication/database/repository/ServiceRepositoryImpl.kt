package com.example.myapplication.database.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.myapplication.businessLogic.repository.ServiceRepository
import com.example.myapplication.database.dao.ServiceDao
import com.example.myapplication.di.AppContainer
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

class ServiceRepositoryImpl(private val serviceDao: ServiceDao): ServiceRepository {
    override suspend fun insert(service: Service) = serviceDao.insert(service)
    override suspend fun update(service: Service) = serviceDao.update(service)
    override suspend fun delete(service: Service) = serviceDao.delete(service)
    override suspend fun getServiceById(id: Int): Service = serviceDao.getServiceById(id)
    override suspend fun getAllServices(): Flow<PagingData<Service>> = Pager(
        config = PagingConfig(
            pageSize = AppContainer.LIMIT,
            enablePlaceholders = false
        ),
        pagingSourceFactory = serviceDao::getAll
    ).flow

    suspend fun clearServices() = serviceDao.deleteAll()
    suspend fun insertServices(services: List<Service>) =
        serviceDao.insert(*services.toTypedArray())
    fun getAllServicesPagingSource(): PagingSource<Int, Service> = serviceDao.getAll()
}