package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.User
import com.example.myapplication.model.UserWithOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun createUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM tbl_user WHERE userId = :id")
    fun getUserById(id: Int): User

    @Query("SELECT * FROM tbl_user WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM tbl_user WHERE userId =:id")
    fun getUserOrders(id: Int) : Flow<UserWithOrder>
}