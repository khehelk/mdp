package com.example.myapplication.api

import com.example.myapplication.api.model.BasketServiceRemote
import com.example.myapplication.api.model.OrderRemote
import com.example.myapplication.api.model.OrderServiceRemote
import com.example.myapplication.api.model.ServiceRemote
import com.example.myapplication.api.model.UserRemote
import com.example.myapplication.api.model.UserRemoteSignIn
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerService {
    //SNEAKER
    @GET("service/get/{id}")
    suspend fun getService(
        @Path("id") id: Int,
    ): ServiceRemote

    @GET("service/getAll")
    suspend fun getServices(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): List<ServiceRemote>

    @POST("service/create")
    suspend fun createService(
        @Body service: ServiceRemote,
    ): ServiceRemote

    @PUT("service/update/{id}")
    suspend fun updateService(
        @Path("id") id: Int,
        @Body service: ServiceRemote
    ): ServiceRemote

    @DELETE("service/delete/{id}")
    suspend fun deleteService(
        @Path("id") id: Int
    )

    //USER
    @POST("user/signup")
    suspend fun SignUp(
        @Body user: UserRemote,
    ): UserRemote

    @POST("user/signin")
    suspend fun SignIn(
        @Body user: UserRemoteSignIn
    ): UserRemote

    //BASKET
    @POST("basket/addServiceToBasket")
    suspend fun addServiceToBasket(
        @Body basketService: BasketServiceRemote
    )

    @GET("basket/getUserBasketServices/{id}")
    suspend fun getUserBasketServices(
        @Path("id") id: Int
    ): List<ServiceRemote>

    @GET("basket/getUserBasket/{id}")
    suspend fun getUserBasket(
        @Path("id") id: Int
    ): Int

    @GET("basket/getQuantity/{basketId}/{serviceId}")
    suspend fun getQuantity(
        @Path("basketId") basketId: Int,
        @Path("serviceId") serviceId: Int,
    ): Int

    @PUT("basket/incrementQuantity/{basketId}/{serviceId}")
    suspend fun increment(
        @Path("basketId") basketId: Int,
        @Path("serviceId") serviceId: Int,
    )

    @PUT("basket/decrementQuantity/{basketId}/{serviceId}")
    suspend fun decrement(
        @Path("basketId") basketId: Int,
        @Path("serviceId") serviceId: Int,
    )

    @GET("basket/getService/{basketId}/{serviceId}")
    suspend fun getService(
        @Path("basketId") basketId: Int,
        @Path("serviceId") serviceId: Int,
    ): Boolean

    @GET("basket/removeService/{basketId}/{serviceId}")
    suspend fun deleteServiceFromBasket(
        @Path("basketId") basketId: Int,
        @Path("serviceId") serviceId: Int,
    )

    @GET("basket/getUserPrice/{userId}")
    suspend fun getTotalPriceForUserBasket(
        @Path("userId") userId: Int
    ): Double

    @GET("basket/deleteAllServiceFromBasket/{basketId}")
    suspend fun deleteAllServiceFromBasket(
        @Path("basketId") basketId: Int
    )

    //ORDER
    @POST("order/addServiceToOrder")
    suspend fun addServiceToOrder(
        @Body orderService: OrderServiceRemote
    )

    @POST("order/create")
    suspend fun createOrder(
        @Body order: OrderRemote
    )

    @GET("order/getUserOrders/{userId}")
    suspend fun getUserOrders(
        @Path("userId") userId: Int
    ) : List<OrderRemote>

    @GET("order/getServiceFromOrder/{orderId}")
    suspend fun getServiceFromOrder(
        @Path("orderId") orderId: Int
    ) : List<ServiceRemote>

    @GET("order/deleteOrder/{orderId}")
    suspend fun deleteOrder(
        @Path("orderId") orderId: Int
    )
    companion object {
        private const val BASE_URL = "https://ftkfjb1l-8080.euw.devtunnels.ms/api/"

        @Volatile
        private var INSTANCE: ServerService? = null

        fun getInstance(): ServerService {
            return INSTANCE ?: synchronized(this) {
                val client = OkHttpClient.Builder()
                    .build()
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
                    .create(ServerService::class.java)
                    .also { INSTANCE = it }
            }
        }
    }
}