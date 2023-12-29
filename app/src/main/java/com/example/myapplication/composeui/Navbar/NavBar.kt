package com.example.myapplication.composeui.Navbar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.BlueNavbar
import com.example.myapplication.ui.theme.GreenBtn

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavBar(){
    val navController = rememberNavController()
    val items = listOf(
        NavItem.ListOfServices,
        NavItem.Basket,
        NavItem.Profile
    )
    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = BlueNavbar,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        ){
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry

            items.forEach { screen ->
                val isSelected = currentDestination?.destination?.route == screen.route

                BottomNavigationItem(
                    selected = isSelected,
                    icon = {
                        Icon(painterResource(screen.icon),
                            null,
                            modifier = if (isSelected){
                                Modifier.background(BlueMain, CircleShape).size(50.dp).padding(8.dp)
                            } else {
                                Modifier.size(50.dp).padding(8.dp)
                            },
                            GreenBtn)
                    },
                    onClick = {
                        navController.navigate(screen.route){
                            if (!isSelected) {
                                navController.graph.startDestinationRoute?.let {
                                    navController.popBackStack(it, inclusive = true)
                                }
                                navController.navigate(screen.route) {
                                    launchSingleTop
                                }
                            }
                            navController.navigate(screen.route)
                        }
                    }
                )
            }
        }
    }){paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(rememberScrollState())) {
        }
        NavController(navController = navController)
    }
}

@Composable
@Preview
fun NavigatePreview(){
    NavBar()
}