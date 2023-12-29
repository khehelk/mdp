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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.BasketViewModel
import com.example.myapplication.businessLogic.viewmodel.ServiceViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun Service(
    navController: NavHostController,
    item: Service,
    basketViewModel: BasketViewModel = viewModel(factory = AppViewModelProvider.Factory),
    serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val user = GlobalUser.getInstance().getUser()
    val basketId by basketViewModel.basketId.collectAsState()
    LaunchedEffect(basketViewModel){
        if(user!=null && basketId == 0) basketViewModel.getUsersBasket(user.userId!!)
    }
    Box(
        modifier = Modifier
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
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
                Image(
                    bitmap = item.photo.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .heightIn(min = 100.dp)
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    contentScale = ContentScale.FillHeight,
                )

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
                            basketViewModel.viewModelScope.launch {
                                serviceViewModel.deleteService(item)
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
                        if(user != null)
                        basketViewModel.viewModelScope.launch {
                            basketViewModel.addToBasket(item.serviceId!!, 1)
                        }
                        else
                            navController.navigate(NavItem.Login.route)
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
                            navController.navigate("change_service/${Gson().toJson(item)}")
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
