package com.example.myapplication.api.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.api.ServerService
import com.example.myapplication.api.ServiceRemoteMediator
import com.example.myapplication.api.model.toService
import com.example.myapplication.api.model.toServiceRemote
import com.example.myapplication.businessLogic.repository.ServiceRepository
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.repository.RemoteKeysRepositoryImpl
import com.example.myapplication.database.repository.ServiceRepositoryImpl
import com.example.myapplication.di.AppContainer
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

class RestServiceRepository(
    private val service: ServerService,
    private val dbServiceRepository: ServiceRepositoryImpl,
    private val database: AppDatabase,
    private val dbRemoteKeyRepository: RemoteKeysRepositoryImpl

) : ServiceRepository {

    override suspend fun getAllServices(): Flow<PagingData<Service>> {
        val pagingSourceFactory = {
            dbServiceRepository.getAllServicesPagingSource()
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = AppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = ServiceRemoteMediator(
                service,
                dbServiceRepository,
                database,
                dbRemoteKeyRepository,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getServiceById(id: Int): Service = service.getService(id).toService()
    override suspend fun insert(service: Service) {
        this.service.createService(service.toServiceRemote())
    }

    override suspend fun update(service: Service) {
        service.serviceId?.let { this.service.updateService(it, service.toServiceRemote()) }
    }

    override suspend fun delete(service: Service) {
        try {
            service.serviceId?.let { this.service.deleteService(it) }
            dbServiceRepository.invalidateService(service.serviceId!!)
        }catch (ex: Exception){}
    }
}