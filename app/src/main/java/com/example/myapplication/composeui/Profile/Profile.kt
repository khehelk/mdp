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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.model.RoleEnum
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.RedBtn

@Composable
fun Profile(navController: NavHostController){
    val user = GlobalUser.getInstance().getUser()
    if(user == null){
        Login(navController = navController)
    }else{
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
        ){
            // TODO: upload profile image
        }
            Box(modifier = Modifier.padding(15.dp)){
                Text(
                    text = user.name + " " + user.surname,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
            Box(modifier = Modifier.padding(15.dp)){
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        
        Button(
            onClick = { navController.navigate(NavItem.ProfileChange.route) },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .padding(vertical = 5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "Change profile",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        Button(
            onClick = { navController.navigate(NavItem.Orders.route) },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .padding(vertical = 5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "Orders",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        if(user.role == RoleEnum.Admin){
            Button(
                onClick = { navController.navigate("add_service/{}") },
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenBtn,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Add service",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
        Button(
            onClick = {
                GlobalUser.getInstance().setUser(null)
                navController.navigate(NavItem.Login.route)
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
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                Icons.Default.ExitToApp,
                contentDescription = null,
                modifier = Modifier,
            )
            Text(
                text = "Exit",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}
}