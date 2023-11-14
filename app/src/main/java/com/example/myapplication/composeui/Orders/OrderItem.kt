package com.example.myapplication.composeui.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Order
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun OrderItem (order: Order){
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
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
    }
}