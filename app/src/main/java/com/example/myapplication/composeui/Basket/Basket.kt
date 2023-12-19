package com.example.myapplication.composeui.Basket

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.BasketViewModel
import com.example.myapplication.businessLogic.viewmodel.OrderViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.Profile.Login
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn

@Composable
fun Basket(navController : NavHostController,
           basketViewModel: BasketViewModel = viewModel(factory = AppViewModelProvider.Factory),
           orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val user = GlobalUser.getInstance().getUser()

    if (user == null){
        Login(navController = navController)
    }else{
        basketViewModel.updateSubTotal(user.userId!!)
        val total = basketViewModel.total.value
        LaunchedEffect(basketViewModel){
             basketViewModel.getBasketServices()
        }
        val serviceList by basketViewModel.myList.collectAsState()
        orderViewModel.updateSelectedItems(serviceList)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueMain)
                .padding(15.dp)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (item in serviceList){
                BasketItemUI(item = item)
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
                            text = "$$total",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Button(
                    onClick = {
                        orderViewModel.createOrder()
                        navController.navigate(NavItem.Orders.route)
                    },
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
}