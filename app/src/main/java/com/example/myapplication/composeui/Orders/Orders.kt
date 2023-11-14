package com.example.myapplication.composeui.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.model.Order
import com.example.myapplication.ui.theme.BlueMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Orders (navController: NavController){
    val ordersList = remember { mutableStateListOf<Order>() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).orderDao().getAllOrder().collect { data ->
                ordersList.clear()
                ordersList.addAll(data)
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMain)
            .padding(15.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        itemsIndexed(ordersList){_, item ->
            OrderItem(item)
        }
    }
}