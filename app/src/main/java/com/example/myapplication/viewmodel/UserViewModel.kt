package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.R
import com.example.myapplication.model.Basket
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.User
import com.example.myapplication.database.repository.BasketRepository
import com.example.myapplication.database.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository, private val basketRepository: BasketRepository): ViewModel() {
    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var photo = mutableIntStateOf(R.drawable.icon_profile)

    fun createUser() = viewModelScope.launch {
        var user = User(
            name = name.value,
            surname = surname.value,
            email = email.value,
            password = password.value,
            role = RoleEnum.User
        )
        userRepository.insert(user)
    }

    fun authUser() = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email.value)
        if(password.value.isNotEmpty() && user.password == password.value){
            val globalUser = GlobalUser.getInstance()
            globalUser.setUser(user)
            val basket = basketRepository.getUsersBasket(user.userId!!)
            if(basket == null){
                basketRepository.createBasket(Basket(null, user.userId))
            }
        }
    }

    fun updateUser() = viewModelScope.launch {
        var updateUser = GlobalUser.getInstance().getUser()
        if(email.value != "")
            updateUser?.email = email.value
        else
            updateUser?.email = updateUser?.email.toString()
        if(name.value != "")
            updateUser?.name = name.value
        else
            updateUser?.name = updateUser?.name.toString()
        if(surname.value != "")
            updateUser?.surname = surname.value
        else
            updateUser?.surname = updateUser?.surname.toString()
        if(password.value != "")
            updateUser?.password = password.value
        else
            updateUser?.password = updateUser?.password.toString()
        //updateUser?.photo =
        if (updateUser != null) {
            userRepository.update(updateUser)
        }
    }

    fun isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
}