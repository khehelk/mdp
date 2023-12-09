package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.composeui.Navbar.NavBar
import com.example.myapplication.model.User
import com.example.myapplication.ui.theme.AppTheme
import com.example.myapplication.ui.theme.BlueMain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme (darkTheme = false){
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlueMain),
                ) {
                    NavBar();
                }
            }
        }
    }
}
class GlobalUser private constructor() {
    private var user: User? = null

    fun setUser(user: User?) {
        this.user = user
    }

    fun getUser(): User? {
        return user
    }

    companion object {
        private var instance: GlobalUser? = null

        fun getInstance(): GlobalUser {
            return instance ?: synchronized(this) {
                instance ?: GlobalUser().also { instance = it }
            }
        }
    }
}
