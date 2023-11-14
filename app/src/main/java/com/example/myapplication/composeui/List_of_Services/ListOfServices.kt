package com.example.myapplication.composeui.List_of_Services

import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListOfServices(navController: NavHostController){
    val serviceList = remember { mutableStateListOf<Service>() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).serviceDao().getAllServices().collect { data ->
                serviceList.clear()
                serviceList.addAll(data)
            }
        }
    }
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
        LazyColumn(modifier = Modifier.padding(15.dp, 0.dp)){
            itemsIndexed(serviceList){_, item ->
                Service(item)
            }
        }
    }
}