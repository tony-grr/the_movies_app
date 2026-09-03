package com.example.themoviesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.themoviesapp.navigation.AppRootNavGraph
import com.example.themoviesapp.navigation.AppRootRoute
import com.example.themoviesapp.presentation.screen.splashscreen.SplashScreen
import com.example.themoviesapp.presentation.theme.TheMoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMoviesAppTheme {
                //отслеживание состояния проверки существования данных для авторизации
                val isAuthDataExistingChecked by viewModel.isAuthDataExistingChecked.collectAsStateWithLifecycle()
                //если проверка существования данных для авторизации выполнена
                if (isAuthDataExistingChecked){
                    val navController = rememberNavController()
                    //отслеживание состояния авторизации
                    val isAuthorized by viewModel.isAuthorized.collectAsStateWithLifecycle()
                    AppRootNavGraph(
                        navHostController = navController,
                        //если пользователь уже авторизован(в системе сохранен токен авторизации),
                        //то стартовым маршрутом навигации будет основной экран приложения
                        //иначе экран авторизации
                        startDestination = if (isAuthorized)
                            AppRootRoute.AppContainer
                        else AppRootRoute.Auth,
                        onLogout = {
                            viewModel.logout()
                        }
                    )
                }
                else {
                    //отображение заставки на время проверки существования токена
                    SplashScreen()
                }
            }
        }
    }
}