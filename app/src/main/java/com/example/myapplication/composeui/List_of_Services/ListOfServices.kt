package com.example.myapplication.composeui.List_of_Services

import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.ServiceViewModel
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueMain

@Composable
fun ListOfServices(navController: NavHostController, serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    LaunchedEffect(serviceViewModel){
        serviceViewModel.getServiceList()
    }
    val services = serviceViewModel.serviceList.collectAsLazyPagingItems()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = "PetMed",
            style = MaterialTheme.typography.bodyMedium
                .copy(Color.White, fontSize = TextUnit(16.0f, TextUnitType.Em))
        )
    }
    LazyColumn(
        modifier = Modifier
            .background(BlueMain)
            .padding(bottom = 60.dp)
    ){
        item{
            SearchBar(
                modifier = Modifier)
            {
                    searchText ->
                //TODO search logic
            }
        }
        items(
            count = services.itemCount,
            key = services.itemKey { service -> service.serviceId!! }
        ){
                index: Int ->
            val service: Service? = services[index]
            if (service != null){
                Service(navController, item = service)
            }
        }
    }
}