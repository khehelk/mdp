package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.database.dao.BasketDao
import com.example.myapplication.database.dao.OrderDao
import com.example.myapplication.database.dao.RemoteKeysDao
import com.example.myapplication.database.dao.ServiceDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.RemoteKeys
import com.example.myapplication.model.Service
import com.example.myapplication.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Service::class, Order::class, OrderService::class, Basket::class, BasketService::class, RemoteKeys::class], version = 9)
abstract class AppDatabase : RoomDatabase(){
    abstract fun serviceDao(): ServiceDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun basketDao(): BasketDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        private const val DB_NAME: String = "my-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        suspend fun populateDatabase() {
//            INSTANCE?.let { database ->
//                // User
//                val userDao = database.userDao()
//                val user1 = User(null, "Danil", "Markov", "danil@mail.ru", "123", RoleEnum.Admin)
//                userDao.insert(user1)
//                val basketDao = database.basketDao()
//                val basket1 = Basket(null, user1.userId!!)
//                basketDao.insert(basket1)
//            }
        }

        fun getInstance(appContext: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase()
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}