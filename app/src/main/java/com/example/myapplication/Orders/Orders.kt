package com.example.myapplication.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.BlueMain
import java.util.Date

@Composable
fun Orders (navController: NavController){
    Column (
        modifier = Modifier
        .fillMaxSize()
        .background(BlueMain)
        .padding(15.dp)
        .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        OrderItem(orderId = 1, date = "11.11.2023", price = 40.0, list = "Inoculation x2")
    }
}