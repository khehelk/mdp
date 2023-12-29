package com.example.myapplication.businessLogic.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.GlobalUser
import com.example.myapplication.api.model.UserRemoteSignIn
import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.businessLogic.repository.UserRepository
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.User

class UserViewModel(
    private val userRepository: UserRepository,
    private val basketRepository: BasketRepository
): MyViewModel() {
    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    fun createUser(photo: Bitmap){
        val user =
            User(
                name = name.value,
                surname = surname.value,
                email = email.value,
                password = password.value,
                role = RoleEnum.User,
                photo = photo
            )
        runInScope(
            actionSuccess = {
                userRepository.insert(user)
            }
        )
    }

    fun authUser() {
        runInScope(
            actionSuccess = {
                val user = userRepository.authUser(UserRemoteSignIn(email.value, password.value))
                GlobalUser.getInstance().setUser(user)
            },
        )
    }

    fun updateUser(user: User){
        runInScope(
            actionSuccess = {
                userRepository.update(user)
            }
        )
    }

    fun isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
}

