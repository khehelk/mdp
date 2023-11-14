package com.example.myapplication.composeui.Navbar


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.composeui.Basket.Basket
import com.example.myapplication.composeui.List_of_Services.AddService
import com.example.myapplication.composeui.List_of_Services.ListOfServices
import com.example.myapplication.composeui.Orders.Orders
import com.example.myapplication.composeui.Profile.Login
import com.example.myapplication.composeui.Profile.Profile
import com.example.myapplication.composeui.Profile.ProfileChange
import com.example.myapplication.composeui.Profile.ProfileNotAuth
import com.example.myapplication.composeui.Profile.Registration

@Composable
fun NavController(navController : NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavItem.ListOfServices.route,

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