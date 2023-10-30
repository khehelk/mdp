package com.example.myapplication.List_of_Services

import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.getServices
import com.example.myapplication.ui.theme.BlueMain

@Composable
fun ListOfServices(navController: NavHostController){
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
            var serviceList = getServices()
            itemsIndexed(serviceList){_, item ->
                Service(item)
            }
        }
    }
}