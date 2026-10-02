package com.example.themoviesapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviesapp.R
import com.example.themoviesapp.presentation.screen.auth.AuthViewModelIntents
import com.example.themoviesapp.presentation.screen.main.MainScreen
import com.example.themoviesapp.presentation.screen.movie_detail.MovieDetailsScreen
import androidx.navigation.compose.navigation
import com.example.themoviesapp.presentation.screen.favorites.FavoritesScreen

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
                    Column {
                        Text(stringResource(R.string.account_screen_title))
                        Button(
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {onLogout()}
                        ){
                            Text(stringResource(R.string.logout_btn_text))
                        }
                    }

                }
            }
        }

    }
}
