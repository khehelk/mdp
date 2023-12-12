package com.example.myapplication.api

import okhttp3.OkHttpClient

interface ServerService {
    companion object {
        private const val BASE_URL = "https://localhost:8080/api/"

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
                    .create(BackendService::class.java)
                    .also { INSTANCE = it }
            }
        }
    }
}