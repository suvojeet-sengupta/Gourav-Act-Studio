package com.suvojeet.gauravactstudio

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suvojeet.gauravactstudio.ui.screens.AboutScreen
import com.suvojeet.gauravactstudio.ui.screens.DetailScreen
import com.suvojeet.gauravactstudio.ui.screens.HomeScreen
import com.suvojeet.gauravactstudio.ui.screens.GalleryScreen
import com.suvojeet.gauravactstudio.ui.screens.PricingScreen
import com.suvojeet.gauravactstudio.ui.screens.ServicesScreen
import com.suvojeet.gauravactstudio.ui.screens.SettingsScreen
import com.suvojeet.gauravactstudio.util.decodeURL
import com.suvojeet.gauravactstudio.util.encodeURL

sealed class Screen(val route: String, @StringRes val title: Int? = null) {
    object Home : Screen("home", R.string.home_screen)
    object Services : Screen("services", R.string.services_screen)
    object Pricing : Screen("pricing", R.string.pricing_screen)
    object Gallery : Screen("gallery", R.string.gallery_screen)
    object About : Screen("about", R.string.about_screen)
    object Settings : Screen("settings", R.string.settings_title)
    object Detail : Screen("detail/{mediaType}/{mediaUrl}") {
        fun createRoute(mediaType: String, mediaUrl: String) = "detail/$mediaType/${mediaUrl.encodeURL()}"
    }
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
        composable(Screen.Gallery.route) {
            GalleryScreen(navController = navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("mediaType") { type = NavType.StringType },
                navArgument("mediaUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val mediaType = backStackEntry.arguments?.getString("mediaType")
            val mediaUrl = backStackEntry.arguments?.getString("mediaUrl")
            if (mediaType != null && mediaUrl != null) {
                DetailScreen(navController = navController, mediaType = mediaType, mediaUrl = mediaUrl.decodeURL())
            }
        }

    }
}