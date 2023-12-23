package com.example.myapplication.composeui.Profile

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.R
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.UserViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn

@Composable
fun ProfileChange (navController: NavHostController, userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val user = GlobalUser.getInstance().getUser()
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
                text = "${user?.name} ${user?.surname}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        Box(modifier = Modifier.padding(15.dp)){
            Text(
                text = "${user?.email}",
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
                MyTextField(label = "Name", onValueChanged = {userViewModel.name.value = it})
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Surname", onValueChanged = {userViewModel.surname.value = it})
            }
            var isEmailValid by remember { mutableStateOf(true) }
            var isPasswordValid by remember { mutableStateOf(true) }

            if (!isEmailValid) {
                Text(
                    text = "Invalid email format",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.Red, fontSize = TextUnit(4.0f, TextUnitType.Em))
                )
            }
            Row (modifier = Modifier
                .padding(vertical = 5.dp)
            ){
                MyTextField(label = "Email", onValueChanged = {
                    userViewModel.email.value = it
                    isEmailValid = userViewModel.isValidEmail()
                })
            }
            if (!isPasswordValid) {
                Text(
                    text = "Password is required",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.Red, fontSize = TextUnit(4.0f, TextUnitType.Em))
                )
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Password", onValueChanged = {
                    userViewModel.password.value = it
                    isPasswordValid = it.isNotEmpty()
                })
            }
        }
        Button(
            onClick = {
                userViewModel.updateUser()
                navController.navigate(NavItem.Profile.route)
            },
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