package com.example.myapplication.businessLogic.repository

import com.example.myapplication.model.RemoteKeyType
import com.example.myapplication.model.RemoteKeys

interface RemoteKeyRepository {
    suspend fun getAllRemoteKeys(id: Int, type: RemoteKeyType): RemoteKeys?
    suspend fun createRemoteKeys(remoteKeys: List<RemoteKeys>)
    suspend fun deleteRemoteKey(type: RemoteKeyType)
}