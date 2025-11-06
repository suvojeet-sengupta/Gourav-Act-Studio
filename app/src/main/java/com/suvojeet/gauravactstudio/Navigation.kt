package com.suvojeet.gauravactstudio

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.suvojeet.gauravactstudio.ui.screens.ContactScreen
import com.suvojeet.gauravactstudio.ui.screens.HomeScreen
import com.suvojeet.gauravactstudio.ui.screens.PortfolioScreen
import com.suvojeet.gauravactstudio.ui.screens.PricingScreen
import com.suvojeet.gauravactstudio.ui.screens.ServicesScreen

sealed class Screen(val route: String, @StringRes val title: Int) {
    object Home : Screen("home", R.string.home_screen)
    object Services : Screen("services", R.string.services_screen)
    object Pricing : Screen("pricing", R.string.pricing_screen)
    object Portfolio : Screen("portfolio", R.string.portfolio_screen)
    object Contact : Screen("contact", R.string.contact_screen)
    object Settings : Screen("settings", R.string.settings_title)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
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
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}