package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.ApiStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

open class MyViewModel : ViewModel() {
    var apiStatus by mutableStateOf(ApiStatus.DONE)

    var apiError by mutableStateOf("")
        private set

    fun runInScope(
        actionSuccess: suspend () -> Unit,
        actionError: suspend () -> Unit
    ) {
        viewModelScope.launch {
            apiStatus = ApiStatus.LOADING
            println(apiStatus)
            runCatching {
                actionSuccess()
                apiStatus = ApiStatus.DONE
                println(apiStatus)
                apiError = ""
            }.onFailure { e: Throwable ->
                when (e) {
                    is IOException ,
                    is HttpException -> {
                        apiStatus = ApiStatus.ERROR
                        println(apiStatus)
                        actionError()
                        apiError = e.localizedMessage ?: e.toString()
                    }

                    else -> throw e
                }
            }
        }
    }

    fun runInScope(actionSuccess: suspend () -> Unit) {
        runInScope(actionSuccess, actionError = {})
    }
}