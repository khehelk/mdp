package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.Modifier
import com.example.myapplication.Navbar.NavBar
import com.example.myapplication.ui.theme.AppTheme
import com.example.myapplication.ui.theme.BlueMain

class App : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme (darkTheme = false){
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(BlueMain),
                ) {
                    NavBar();
                }
            }
        }
    }
}
