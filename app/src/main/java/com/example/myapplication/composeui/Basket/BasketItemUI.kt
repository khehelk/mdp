package com.example.myapplication.composeui.Basket

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.GlobalUser
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.BasketViewModel
import com.example.myapplication.businessLogic.viewmodel.OrderViewModel
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import kotlinx.coroutines.launch

@Composable
fun BasketItemUI(item: Service, basketViewModel: BasketViewModel = viewModel(factory = AppViewModelProvider.Factory), orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val user = GlobalUser.getInstance().getUser()
    val basketId = user?.basketId!!
    val quantityState: Int by basketViewModel.getQuantityState(basketId, item.serviceId!!).collectAsState(0)
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .height(150.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(color = Color.White, RoundedCornerShape(15.dp))
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
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
            ) {
                Text(
                    text = item.name,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$${item.price}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$quantityState",
                        color = TextPrimary
                    )
                    Column(verticalArrangement = Arrangement.SpaceAround) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    basketViewModel.incrementServiceQuantity(basketId, item.serviceId!!)
                                }
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .fillMaxWidth()
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenBtn,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Text(text = "+")
                        }
                        Button(
                            onClick = {
                                basketViewModel.viewModelScope.launch{
                                    basketViewModel.decrementOrRemoveServiceQuantity(basketId, item.serviceId!!)
                                }
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .fillMaxWidth()
                                .clip(CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenBtn,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Text(text = "-")
                        }
                    }
                }
            }
        }
    }
}
