package com.example.myapplication.composeui.List_of_Services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.viewmodel.AppViewModelProvider
import com.example.myapplication.viewmodel.BasketViewModel
import com.example.myapplication.viewmodel.ServiceViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun Service(navController: NavHostController, item: Service, basketViewModel: BasketViewModel = viewModel(factory = AppViewModelProvider.Factory), serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val user = GlobalUser.getInstance().getUser()
    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .height(150.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            ),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(color = Color.White, RoundedCornerShape(15.dp))
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            item.photo?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .heightIn(min = 100.dp)
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    contentScale = ContentScale.FillHeight,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                verticalArrangement = Arrangement.Top,
            ){
                Text(
                    text = item.name,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium)
                if(user?.role == RoleEnum.Admin){
                    Button(
                        onClick = {
                            runBlocking {
                                launch(Dispatchers.Default){
                                    serviceViewModel.deleteService(item)
                                }
                            }
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenBtn,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(0.dp),
                    ){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = "$${item.price}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Button(
                    onClick = {
                        runBlocking{
                            launch(Dispatchers.Default){
                                basketViewModel.addToBasket(BasketService(basketViewModel.getUsersBasket(user?.userId!!).basketId!!, item.serviceId!!, 1))
                            }
                        }
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenBtn,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp),
                ){
                    Text(text = "+")
                }
                if(user?.role == RoleEnum.Admin){
                    Button(
                        onClick = {
                            serviceViewModel.service.value = item
                            navController.navigate("add_service/${Gson().toJson(item)}")
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenBtn,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(0.dp),
                    ){
                        Icon(imageVector = Icons.Default.Create, contentDescription = null)
                    }
                }
            }
        }
    }
}
