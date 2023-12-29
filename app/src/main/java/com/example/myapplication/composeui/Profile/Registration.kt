package com.example.myapplication.composeui.Profile

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.api.ApiStatus
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.UserViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.NetworkUI.Loading
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextSecondary

@Composable
fun Registration (navController: NavController, userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val context = LocalContext.current
    if (userViewModel.apiStatus == ApiStatus.ERROR){
        Toast.makeText(context, "Error: " + userViewModel.apiError, Toast.LENGTH_SHORT).show()
        return
    }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    val photo = BitmapFactory.decodeResource(context.resources, R.drawable.profile)

    when(userViewModel.apiStatus) {
        ApiStatus.LOADING -> Loading()
        ApiStatus.DONE ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueMain)
                .padding(15.dp)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(
                    text = "PetMed",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.White, fontSize = TextUnit(16.0f, TextUnitType.Em))
                )
            }
            Column (
            ){
                Row (modifier = Modifier.padding(vertical = 5.dp)){
                    MyTextField(label = "Name", onValueChanged = { userViewModel.name.value = it })
                }
                Row (modifier = Modifier.padding(vertical = 5.dp)){
                    MyTextField(label = "Surname", onValueChanged = { userViewModel.surname.value = it })
                }
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

                Row (modifier = Modifier
                    .padding(vertical = 5.dp)
                ){
                    MyTextField(label = "Password", visualTransformation = PasswordVisualTransformation(), onValueChanged = {
                        userViewModel.password.value = it
                        isPasswordValid = it.isNotEmpty()
                    })
                }
            }
            Button(
                onClick = {
                    if (userViewModel.email.value == ""
                        || userViewModel.name.value == ""
                        || userViewModel.surname.value == ""
                        || !isEmailValid
                        || !isPasswordValid)
                        Toast.makeText(context, "Input correct value!", Toast.LENGTH_SHORT).show()
                    else {
                        userViewModel.createUser(photo)
                        navController.navigate(NavItem.Login.route)
                    }
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
                Text(
                    text = "Registration",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.White)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Already have a account? ",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(TextSecondary)
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(GreenBtn),
                    modifier = Modifier.clickable { navController.navigate(NavItem.Login.route) }
                )
            }
        }

        else -> {}
    }
}