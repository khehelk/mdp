package com.example.myapplication.composeui.Navbar

import androidx.annotation.DrawableRes
import com.example.myapplication.R

sealed class NavItem(
    val route: String,
    @DrawableRes val icon: Int
){
    object ListOfServices : NavItem(
        "list_of_services",
        R.drawable.icon_list_of_services
    )
    object Basket : NavItem(
        "basket",
        R.drawable.icon_basket
    )
    object Profile : NavItem(
        "profile",
        R.drawable.icon_profile
    )
    object ProfileChange : NavItem(
        "profile_change",
        R.drawable.icon_profile
    )
    object Orders : NavItem(
        "orders",
        R.drawable.icon_profile
    )
    object ProfileNotAuth : NavItem(
        "profile_not_auth",
        R.drawable.icon_profile
    )
    object Login : NavItem(
        "login",
        R.drawable.icon_profile
    )
    object Registration : NavItem(
        "registration",
        R.drawable.icon_profile
    )
    object AddService : NavItem(
        "add_service",
        R.drawable.icon_list_of_services
    )
}