package com.example.myapplication.composeui.Profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.myapplication.composeui.UIComponents.DatePicker
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.RedBtn

@Composable
fun ReportDatePick(reportViewModel: ReportViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val context = LocalContext.current
    if (reportViewModel.apiStatus == ApiStatus.ERROR){
        Toast.makeText(context, "Error: " + reportViewModel.apiError, Toast.LENGTH_SHORT).show()
        return
    }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "PetMed",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.White, fontSize = TextUnit(8.0f, TextUnitType.Em))
                )
            }
            DatePicker(
                selectedDate = reportViewModel.dateFrom,
                onDateSelected = { date ->
                    reportViewModel.dateFrom.value = date
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DatePicker(
                selectedDate = reportViewModel.dateTo,
                onDateSelected = { date ->
                    reportViewModel.dateTo.value = date
                },
            )
            Button(
                onClick = {
                    if(reportViewModel.dateFrom.value <= reportViewModel.dateTo.value){
                        reportViewModel.updateReportData(reportViewModel.dateFrom.value, reportViewModel.dateTo.value)
                    }else{
                        Toast.makeText(context, "Incorrect date!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedBtn,
                    contentColor = Color.White
                ),
            ) {
                Text("Get reports")
            }
        }

        else -> {}
    }
}