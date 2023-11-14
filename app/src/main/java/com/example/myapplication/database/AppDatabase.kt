package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.R
import com.example.myapplication.dao.BasketDao
import com.example.myapplication.dao.OrderDao
import com.example.myapplication.dao.ServiceDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.Service
import com.example.myapplication.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [User::class, Service::class, Order::class, OrderService::class, Basket::class, BasketService::class], version = 4)
abstract class AppDatabase : RoomDatabase(){
    abstract fun serviceDao(): ServiceDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun basketDao(): BasketDao

    companion object {
        private const val DB_NAME: String = "my-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        suspend fun populateDatabase() {
            INSTANCE?.let { database ->
                // User
                val userDao = database.userDao()
                val user1 = User(null, "Artem", "Emelyanov", "artem@mail.ru", "123", RoleEnum.User)
                val user2 = User(null, "Danil", "Markov", "danil@mail.ru", "123", RoleEnum.Admin)
                val user3 = User(null, "Viktoria", "Presnyakova", "vika@mail.ru", "123", RoleEnum.User)
                userDao.createUser(user1)
                userDao.createUser(user2)
                userDao.createUser(user3)
                // Service
                val serviceDao = database.serviceDao()
                val service1 = Service(null, "Test", 19.09, R.drawable.image_service)
                val service2 = Service(null, "Test", 19.09, R.drawable.image_service)
                val service3 = Service(null, "Test", 19.09, R.drawable.image_service)
                val service4 = Service(null, "Test", 19.09, R.drawable.image_service)
                val serviceId1 = serviceDao.insert(service1).toInt()
                val serviceId2 = serviceDao.insert(service2).toInt()
                serviceDao.insert(service3)
                serviceDao.insert(service4)
                // Order
                val orderDao = database.orderDao()
                val order1 = Order(null, Date().time, 200.00, 1)
                val order2 = Order(null, Date().time, 200.00, 1)
                val orderId1 = orderDao.createOrder(order1).toInt()
                val orderId2 = orderDao.createOrder(order2).toInt()
                orderDao.insertOrderService(OrderService(orderId1, serviceId1))
                orderDao.insertOrderService(OrderService(orderId2, serviceId2))
                // Basket
                val basketDao = database.basketDao()
                val basket = Basket(null, Date().time, 200.00, 1)
                val basketId = basketDao.createBasket(basket).toInt()
                basketDao.insertBasketService(BasketService(null, basketId, serviceId1))
                basketDao.insertBasketService(BasketService(null, basketId, serviceId1))
            }
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