package com.example.yassir_test.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yassir_test.ui.screen.DetailScreen
import com.example.yassir_test.ui.screen.HomeScreen
import com.example.yassir_test.viewmodel.MovieViewModel

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val movieViewModel: MovieViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController, movieViewModel = movieViewModel)
        }
        composable(route = Screen.Details.route){
            DetailScreen(movieViewModel= movieViewModel)
        }
    }
}