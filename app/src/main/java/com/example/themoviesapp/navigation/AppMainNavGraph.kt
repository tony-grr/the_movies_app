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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviesapp.presentation.screen.auth.AuthViewModelIntents

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
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ){
            NavHost(
                navController = innerNavController,
                startDestination = AppMainRoute.Main,
            ){
                composable<AppMainRoute.Main>{
                    Text("Main Screen")
                }
                composable<AppMainRoute.Favorites>{
                    Text("Favorites Screen")
                }
                composable<AppMainRoute.Account>{
                    Column {
                        Text("Account Screen")
                        Button(
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {onLogout()}
                        ){
                            Text("Logout")
                        }
                    }

                }
            }
        }

    }
}