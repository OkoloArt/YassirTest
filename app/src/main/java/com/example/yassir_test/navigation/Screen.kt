package com.example.yassir_test.navigation

sealed class Screen (val route: String){
    data object Home: Screen(route = "home")
    data object Details: Screen(route = "details")
}