package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

enum class RemoteKeyType(private val type: String) {
    SERVICE(Service::class.simpleName ?: "Service");
    @TypeConverter
    fun toRemoteKeyType(value: String) = RemoteKeyType.values().first { it.type == value }
    @TypeConverter
    fun fromRemoteKeyType(value: RemoteKeyType) = value.type
}
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val entityId: Int,
    @TypeConverters(RemoteKeyType::class)
    val type: RemoteKeyType,
    val prevKey: Int?,
    val nextKey: Int?
)