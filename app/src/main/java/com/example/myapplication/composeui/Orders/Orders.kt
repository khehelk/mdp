package com.example.myapplication.composeui.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.OrderViewModel
import com.example.myapplication.ui.theme.BlueMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Orders (navController: NavController, orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val user = GlobalUser.getInstance().getUser()
    val ordersList by orderViewModel.orders.collectAsState()
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            orderViewModel.getUserOrders(user?.userId!!)
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