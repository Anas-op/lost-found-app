package com.anas.lostfound.feature_lostfound.presentation.util

sealed class Screen(val route: String) {
    // screen pages
    object LostFoundScreen: Screen("items_screen")
    object NewLostItemScreen: Screen("newLost_screen")
    object NewFoundItemScreen: Screen("newFound_screen")
    object LoginScreen: Screen("login")
    object SignupScreen: Screen("signup")

}