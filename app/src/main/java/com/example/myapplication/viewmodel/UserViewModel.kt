package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.R
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.User
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var photo = mutableIntStateOf(R.drawable.icon_profile)

    fun createUser() = viewModelScope.launch {
        val user = User(
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
        if(!password.value.isEmpty() and (user.password == password.value)){
            val globalUser = GlobalUser.getInstance()
            globalUser.setUser(user)
        }
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}