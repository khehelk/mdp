package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GlobalUser
import com.example.myapplication.R
import com.example.myapplication.api.model.UserRemoteSignIn
import com.example.myapplication.businessLogic.repository.BasketRepository
import com.example.myapplication.businessLogic.repository.UserRepository
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository, private val basketRepository: BasketRepository): ViewModel() {
    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var photo = mutableIntStateOf(R.drawable.icon_profile)
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)
//    private val _loggedUser = MutableStateFlow(User(null,"null","null","null","null",RoleEnum.User))
//    val loggedUser: StateFlow<User> = _loggedUser.asStateFlow()

    fun createUser() = viewModelScope.launch {
        val user = User(
            name = name.value,
            surname = surname.value,
            email = email.value,
            password = password.value,
            role = RoleEnum.User,
            photo = R.drawable.icon_profile
        )
//        isBusy = true
        userRepository.insert(user)
//        isBusy = false
    }

    fun authUser() = viewModelScope.launch {
//        isBusy = true
        val user = userRepository.authUser(UserRemoteSignIn(email.value, password.value))
//        isLoggedIn = true
//        isBusy = false
        GlobalUser.getInstance().setUser(user)
    }

    fun updateUser() = viewModelScope.launch {
        val updateUser = GlobalUser.getInstance().getUser()
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

