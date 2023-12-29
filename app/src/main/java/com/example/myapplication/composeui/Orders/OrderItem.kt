package com.example.myapplication.composeui.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.OrderViewModel
import com.example.myapplication.model.Order
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun OrderItem (
    order: Order,
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    var services by remember { mutableStateOf<List<Service>>(emptyList()) }
    var expanded = remember { mutableStateOf(false) }
    LaunchedEffect(order.orderId) {
        services = orderViewModel.getOrderWithServices(order.orderId!!).first()
    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                expanded.value = !expanded.value
            }
    ){
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
        ){
            Text(text = "Order:${order.orderId}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
            Text(text = "${order.total}", style = MaterialTheme.typography.bodyMedium)
        }
        Row (
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        ){
            Text(text = dateFormat.format(Date(order.date)), style = MaterialTheme.typography.bodyMedium)
        }
        if(expanded.value){
            LazyRow(){
                itemsIndexed(services ?: emptyList()) { index, service ->
                    OrderScrollService(service)
                }
            }
        }
    }
}