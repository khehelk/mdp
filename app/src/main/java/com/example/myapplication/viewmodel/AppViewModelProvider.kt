package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.App

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ServiceViewModel(app().container.serviceRepo)
        }
        initializer {
            UserViewModel(app().container.userRepo, app().container.basketRepo)
        }
        initializer {
            OrderViewModel(app().container.orderRepo, app().container.basketRepo)
        }
        initializer {
            BasketViewModel(app().container.basketRepo, app().container.orderRepo,)
        }
    }
}

fun CreationExtras.app(): App =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)