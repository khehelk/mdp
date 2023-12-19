package com.example.myapplication.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.Service

@Dao
interface ServiceDao {
    @Insert
    suspend fun insert(vararg service: Service)
    @Update
    suspend fun update(service: Service)
    @Delete
    suspend fun delete(service: Service)
    @Query("SELECT*FROM tbl_service")
    fun getAllServicesPaged(): PagingSource<Int, Service>
    @Query("select * from tbl_service")
    fun getAll(): PagingSource<Int, Service>
    @Query("SELECT * FROM tbl_service WHERE serviceId = :id")
    suspend fun getServiceById(id: Int): Service
    @Query("DELETE FROM tbl_service")
    suspend fun deleteAll()
}