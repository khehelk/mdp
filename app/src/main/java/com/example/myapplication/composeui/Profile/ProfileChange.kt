package com.example.myapplication.composeui.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.ui.theme.BlueBorder
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextSecondary

@Composable
fun ProfileChange (navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMain)
            .padding(15.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Box(modifier = Modifier
            .clip(CircleShape)
            .size(200.dp)
            .background(Color.White)
            .padding(PaddingValues(0.dp))
        ){
            Icon(
                painterResource(id = R.drawable.upload),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                GreenBtn
            )
        }
        Box(modifier = Modifier.padding(15.dp)){
            Text(
                text = "Name Surname",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        Box(modifier = Modifier.padding(15.dp)){
            Text(
                text = "example@mail.ex",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        Column (
        ){
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Name", onValueChanged = {})
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Surname", onValueChanged = {})
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Email", onValueChanged = {})
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Old password", onValueChanged = {})
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "New password", onValueChanged = {})
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(60.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = "Confirm changes", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
        }
    }
}