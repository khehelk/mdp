package com.example.myapplication.Orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondary
import java.util.Date

@Composable
fun OrderItem (orderId: Int, date: String, price: Double, list: String){
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))){
        Column (
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    clip = false
                )
        ){
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
            ){
                Text(text = "Order:$orderId", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                Text(text = "$price$", style = MaterialTheme.typography.bodyMedium)
            }
            Row (
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
            ){
                Text(text = date, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row (
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(15.dp)
        ){
            Text(text = list, style = MaterialTheme.typography.bodyMedium.copy(TextSecondary))
        }
    }
}