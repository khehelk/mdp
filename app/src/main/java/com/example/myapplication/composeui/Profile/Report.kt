package com.example.myapplication.composeui.Profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.api.ApiStatus
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.ReportViewModel
import com.example.myapplication.composeui.NetworkUI.Loading
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.TextPrimary

@Composable
fun Report(reportViewModel: ReportViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val context = LocalContext.current
    if (reportViewModel.apiStatus == ApiStatus.ERROR){
        Toast.makeText(context, "Error: " + reportViewModel.apiError, Toast.LENGTH_SHORT).show()
        return
    }
    if(reportViewModel.countOrder.value == 0){
        ReportDatePick()
    }
    else{
        when(reportViewModel.apiStatus){
            ApiStatus.LOADING -> Loading()
            ApiStatus.DONE ->
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlueMain)
                        .padding(15.dp)
                        .padding(bottom = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Column(modifier = Modifier
                        .background(Color.White, RoundedCornerShape(15.dp)).padding(10.dp)
                    ){
                        Text(
                            text = "Number of orders: ${reportViewModel.countOrder.value}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                TextPrimary, fontSize = TextUnit(4.0f, TextUnitType.Em)),
                            color = TextPrimary
                        )
                        Text(
                            text = "Total earn: ${reportViewModel.totalEarn.value}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                TextPrimary, fontSize = TextUnit(4.0f, TextUnitType.Em)),
                            color = TextPrimary
                        )
                        Text(
                            text = "Average check cost: ${reportViewModel.avgSum.value}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                TextPrimary, fontSize = TextUnit(4.0f, TextUnitType.Em)),
                            color = TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    Box(modifier = Modifier
                        .background(Color.White, RoundedCornerShape(15.dp))
                        .padding(10.dp)
                    ){
                        Text(
                            text = "Top 10 popular services:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                TextPrimary, fontSize = TextUnit(4.0f, TextUnitType.Em)),
                            color = TextPrimary
                        )
                    }
                    LazyRow {
                        itemsIndexed(reportViewModel.serviceList.value ?: emptyList()) { index, service ->
                            ServiceReportCard(service)
                        }
                    }
                }

            else -> {}
        }
    }
}