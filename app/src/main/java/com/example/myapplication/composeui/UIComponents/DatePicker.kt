package com.example.myapplication.composeui.UIComponents

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DatePicker(
    selectedDate: MutableState<Long>,
    onDateSelected: (Long) -> Unit,
    value: Long? = null
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val date = Date()

    val datePickerDialog = remember { mutableStateOf(DatePickerDialog(context)) }
    val dateFormatter = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selected Date: ${dateFormatter.format(selectedDate.value)}",
            color = TextPrimary,
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .padding(vertical = 5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            onClick = {
                datePickerDialog.value = DatePickerDialog(
                    context,
                    { _, year: Int, month: Int, dayOfMonth: Int ->
                        val selectedDateInMillis = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.timeInMillis

                        selectedDate.value = selectedDateInMillis
                        onDateSelected(selectedDateInMillis)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.value.show()
            }
        ) {
            Text(text = "Open Date Picker")
        }
    }
}