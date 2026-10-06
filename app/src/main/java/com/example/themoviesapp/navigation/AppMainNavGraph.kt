package com.example.themoviesapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.themoviesapp.presentation.screen.account.AccountScreen
import com.example.themoviesapp.presentation.screen.favorites.FavoritesScreen
import com.example.themoviesapp.presentation.screen.main.MainScreen
import com.example.themoviesapp.presentation.screen.movie_detail.MovieDetailsScreen

@Composable
fun AppMainNavGraph(
    onLogout:()-> Unit
){
    val innerNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(innerNavController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            NavHost(
                navController = innerNavController,
                startDestination = AppMainRoute.Main,
            ){
                navigation<AppMainRoute.Main>(startDestination = AppMainRoute.Main.List) {
                    composable<AppMainRoute.Main.List> {
                        MainScreen(onMovieClick = { movieId ->
                            innerNavController.navigate(AppMainRoute.Main.Detail(movieId))
                        })
                    }
                    composable<AppMainRoute.Main.Detail> {
                        MovieDetailsScreen(
                            onNavigateBack = { innerNavController.popBackStack() }
                        )
                    }
                }
                composable<AppMainRoute.Favorites>{
                    FavoritesScreen(
                        onMovieClick = { movieId ->
                            innerNavController.navigate(AppMainRoute.Main.Detail(movieId))
                        }
                    )
                }
                composable<AppMainRoute.Account>{
                    AccountScreen(
                        onLogout = onLogout
                    )
                }
            }
        }

    }
}
