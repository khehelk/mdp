package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Insert
    suspend fun insert(service: Service) : Long

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)

    @Query("SELECT*FROM tbl_service")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM tbl_service WHERE serviceId = :id")
    suspend fun getServiceById(id: Int): Service
}