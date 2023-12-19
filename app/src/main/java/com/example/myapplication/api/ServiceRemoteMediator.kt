package com.example.myapplication.api

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.api.model.toService
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.repository.RemoteKeysRepositoryImpl
import com.example.myapplication.database.repository.ServiceRepositoryImpl
import com.example.myapplication.model.RemoteKeyType
import com.example.myapplication.model.RemoteKeys
import com.example.myapplication.model.Service
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ServiceRemoteMediator(
    private val service: ServerService,
    private val serviceRepository: ServiceRepositoryImpl,
    private val database: AppDatabase,
    private val dbRemoteKeyRepository: RemoteKeysRepositoryImpl
) : RemoteMediator<Int, Service>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Service>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val services = service.getServices(page, state.config.pageSize).map { it.toService() }
            val endOfPaginationReached = services.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dbRemoteKeyRepository.deleteRemoteKey(RemoteKeyType.SERVICE)
                    serviceRepository.clearServices()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = services.map {
                    RemoteKeys(
                        entityId = it.serviceId!!,
                        type = RemoteKeyType.SERVICE,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                dbRemoteKeyRepository.createRemoteKeys(keys)
                serviceRepository.insertServices(services)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Service>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { service ->
                service.serviceId?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.SERVICE) }
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Service>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { service ->
                service.serviceId?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.SERVICE) }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Service>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.serviceId?.let { serviceUid ->
                dbRemoteKeyRepository.getAllRemoteKeys(serviceUid, RemoteKeyType.SERVICE)
            }
        }
    }
}