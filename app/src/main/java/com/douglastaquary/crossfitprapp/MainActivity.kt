package com.douglastaquary.crossfitprapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.douglastaquary.crossfitprapp.ui.NavigationItem
import com.douglastaquary.crossfitprapp.ui.screens.InsightScreen
import com.douglastaquary.crossfitprapp.ui.screens.SettingsScreen
import com.douglastaquary.crossfitprapp.ui.screens.today.TodayScreen
import com.douglastaquary.crossfitprapp.ui.theme.CrossfitPRAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
        CrossfitPRAppTheme {
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) },
                contentColor = MaterialTheme.colors.background
            ) {
                Navigation(navController = navController)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Today.route) {
        composable(NavigationItem.Today.route) {
            TodayScreen()
        }
        composable(NavigationItem.Insights.route) {
            InsightScreen()
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen()
        }
    }
}

// TopBAR
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp, color = MaterialTheme.colors.onBackground) },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.background
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Today,
        NavigationItem.Insights,
        NavigationItem.Settings,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = MaterialTheme.colors.onBackground,
                unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
