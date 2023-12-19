package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM tbl_user WHERE userId = :id")
    suspend fun getUserById(id: Int): User

    @Query("SELECT * FROM tbl_user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User
}