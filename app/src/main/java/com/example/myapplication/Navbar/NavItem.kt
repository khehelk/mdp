package com.example.myapplication.Navbar

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
}