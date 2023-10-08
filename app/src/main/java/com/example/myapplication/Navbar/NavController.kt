package com.example.myapplication.Navbar


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.Basket.Basket
import com.example.myapplication.List_of_Services.ListOfServices
import com.example.myapplication.Profile.Profile

@Composable
fun NavController(navController : NavHostController){
    NavHost(
        navController = navController,
        startDestination = "list_of_services"
    ){
        composable(
            "list_of_services"
        ){
            ListOfServices()
        }
        composable(
            "basket/{userId}"
        ){
            backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                Basket(
                    navController,
                    it.toLong()
                )
            }
        }
        composable(
            "profile/{userId}"
        ){
            backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                Profile(
                    navController,
                    it.toLong()
                )
            }
        }
    }
}