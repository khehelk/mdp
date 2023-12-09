package com.example.myapplication.composeui.List_of_Services

import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.viewmodel.AppViewModelProvider
import com.example.myapplication.viewmodel.ServiceViewModel

@Composable
fun ListOfServices(navController: NavHostController, serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMain)
            .padding(bottom = 60.dp)
    ){
        SearchBar(
            modifier = Modifier)
        {
            searchText ->
            //TODO search logic
        }
        val services = serviceViewModel.serviceList.collectAsLazyPagingItems()
        LazyColumn(modifier = Modifier.padding(15.dp, 0.dp)){
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
}