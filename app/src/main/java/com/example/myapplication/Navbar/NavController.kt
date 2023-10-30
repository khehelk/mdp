package com.example.myapplication.Navbar


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.Basket.Basket
import com.example.myapplication.List_of_Services.AddService
import com.example.myapplication.List_of_Services.ListOfServices
import com.example.myapplication.Orders.Orders
import com.example.myapplication.Profile.Login
import com.example.myapplication.Profile.Profile
import com.example.myapplication.Profile.ProfileChange
import com.example.myapplication.Profile.ProfileNotAuth
import com.example.myapplication.Profile.Registration

@Composable
fun NavController(navController : NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavItem.Profile.route
    ){
        composable(
            NavItem.ListOfServices.route
        ){
            ListOfServices(navController)
        }
        composable(
//            "${NavItem.Basket.route}/{userId}"
            "${NavItem.Basket.route}"
        ){
            Basket(navController)
//            backStackEntry ->
//            backStackEntry.arguments?.getString("userId")?.let {
//                Basket(
//                    navController,
//                    it.toLong()
//                )
//            }
        }
        composable(
//            "${NavItem.Profile.route}/{userId}"
            "${NavItem.Profile.route}"
        ){
            Profile(navController)
//            backStackEntry ->
//            backStackEntry.arguments?.getString("userId")?.let {
//                Profile(
//                    navController,
//                    it.toLong()
//                )
//            }
        }
        composable(
            "${NavItem.ProfileChange.route}"
        ){
            ProfileChange(navController)
        }
        composable(
            "${NavItem.Orders.route}"
        ){
            Orders(navController)
        }
        composable(
            "${NavItem.ProfileNotAuth.route}"
        ){
            ProfileNotAuth(navController)
        }
        composable(
            "${NavItem.Login.route}"
        ){
            Login(navController)
        }
        composable(
            "${NavItem.Registration.route}"
        ){
            Registration(navController)
        }
        composable(
            "${NavItem.AddService.route}"
        ){
            AddService(navController)
        }
    }
}