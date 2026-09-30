package com.example.themoviesapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomNavigationBar(
    navController: NavHostController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column{
        HorizontalDivider(
            thickness = 1.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            AppMainRoute.items.forEach { navItem->
                NavigationBarItem(
                    icon = { Icon(painterResource(navItem.iconResourceId), contentDescription = stringResource( navItem.labelResourceId)) },
                    label = { Text(text = stringResource( navItem.labelResourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(navItem::class) } == true,
                    onClick = {
                        navController.navigate(navItem) {
                            popUpTo<AppMainRoute.Main> {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        selectedTextColor = MaterialTheme.colorScheme.tertiary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }

}