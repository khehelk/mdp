package com.example.myapplication.database.repository

import com.example.myapplication.database.dao.RemoteKeysDao
import com.example.myapplication.model.RemoteKeyType
import com.example.myapplication.model.RemoteKeys

class RemoteKeysRepositoryImpl(private val remoteKeysDao: RemoteKeysDao) {
    suspend fun getAllRemoteKeys(id: Int, type: RemoteKeyType) =
        remoteKeysDao.getRemoteKeys(id, type)

    suspend fun createRemoteKeys(remoteKeys: List<RemoteKeys>) =
        remoteKeysDao.insertAll(remoteKeys)

    suspend fun deleteRemoteKey(type: RemoteKeyType) =
        remoteKeysDao.clearRemoteKeys(type)
}