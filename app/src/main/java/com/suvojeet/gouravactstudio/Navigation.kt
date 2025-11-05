package com.suvojeet.gouravactstudio

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.suvojeet.gouravactstudio.ui.screens.ContactScreen
import com.suvojeet.gouravactstudio.ui.screens.HomeScreen
import com.suvojeet.gouravactstudio.ui.screens.PortfolioScreen
import com.suvojeet.gouravactstudio.ui.screens.PricingScreen
import com.suvojeet.gouravactstudio.ui.screens.ServicesScreen

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Services : Screen("services", "Services")
    object Pricing : Screen("pricing", "Pricing")
    object Portfolio : Screen("portfolio", "Portfolio")
    object Contact : Screen("contact", "Contact")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Services.route) {
            ServicesScreen()
        }
        composable(Screen.Pricing.route) {
            PricingScreen()
        }
        composable(Screen.Portfolio.route) {
            PortfolioScreen()
        }
        composable(Screen.Contact.route) {
            ContactScreen()
        }
    }
}