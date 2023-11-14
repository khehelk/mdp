package com.example.myapplication.composeui.Basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@Composable
fun Basket(navController : NavHostController){
    val basketList = remember { mutableStateMapOf<Service, Int>() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).basketDao().getBasketWithServices(1).collect { data ->
                basketList.clear()
                for (item in data.services){
                    basketList[item] = (basketList[item] ?: 0) + 1
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMain)
            .padding(15.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_calendar),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
        )
        LazyColumn {
            items(basketList.toList()) { (service, count) ->
                key(service.serviceId) {
                    BasketItemUI(service, count){ newCount ->
                        basketList[service] = newCount
                    }
                }
            }
        }
        Box(modifier = Modifier
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Color.Transparent)
            .height(130.dp),
        ){
            Column (modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
                .padding(PaddingValues(15.dp)),
            ){
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(
                        text = "Total: ",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = (getTotalPrice(basketList)).toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(
                        text = "Date:",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "11.11.2023:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenBtn,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(text = "Confirm order", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
            }
        }
    }
}

fun getTotalPrice(basketList: SnapshotStateMap<Service, Int>): Double {
    var price = 0.00
    for (item in basketList){
        price += item.key.price * item.value
    }
    return (price*100).roundToInt() / 100.0
}