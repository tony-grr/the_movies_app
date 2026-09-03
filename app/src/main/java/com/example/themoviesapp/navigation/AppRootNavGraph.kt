package com.example.themoviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.themoviesapp.presentation.screen.auth.AuthScreen

@Composable
fun AppRootNavGraph(
    navHostController: NavHostController,
    startDestination: AppRootRoute = AppRootRoute.Auth,
    onLogout:()-> Unit
){
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable<AppRootRoute.Auth> {
           AuthScreen {
               //коллбэк успешной авторизации
               //будет происходить навигация по маршруту AppRootRoute.AppContainer
               navHostController.navigate(AppRootRoute.AppContainer){
                   //удаление всех маршрутов навигации которые были до AppRootRoute.Auth включительно
                   //чтобы пользователь не смог вернуться на экран авторизации
                   popUpTo(AppRootRoute.Auth) { inclusive = true }
                   launchSingleTop = true
               }
           }
        }
        composable<AppRootRoute.AppContainer> {
            AppMainNavGraph {
                //коллбэк логаута
                //выполнение навигации на экран авторизации
                navHostController.navigate(AppRootRoute.Auth){
                    //удаление всех маршрутов навигации которые были до AppRootRoute.AppContainer включительно
                    //чтобы пользователь не смог вернуться на главный экран
                    popUpTo(AppRootRoute.AppContainer) { inclusive = true }
                    launchSingleTop = true
                }
                onLogout()
            }
        }
    }
}