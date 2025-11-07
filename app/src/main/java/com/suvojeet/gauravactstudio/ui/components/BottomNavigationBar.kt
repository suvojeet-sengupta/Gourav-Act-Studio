package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.suvojeet.gauravactstudio.Screen

data class BottomNavItem(
    val screen: Screen,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
    val label: String
)

 @Composable
 fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            Icons.Outlined.Home,
            "Home"
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.CameraAlt,
            Icons.Outlined.CameraAlt,
            "Services"
        ),
        BottomNavItem(
            Screen.Gallery,
            Icons.Filled.PhotoLibrary,
            Icons.Outlined.PhotoLibrary,
            "Gallery"
        ),
        BottomNavItem(
            Screen.Pricing,
            Icons.Filled.MonetizationOn,
            Icons.Outlined.MonetizationOn,
            "Pricing"
        ),
        BottomNavItem(
            Screen.About,
            Icons.Filled.Info,
            Icons.Outlined.Info,
            "About"
        )
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Rounded corners
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == item.screen.route
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            // saveState = true // Removed to prevent state restoration
                        }
                        launchSingleTop = true
                        // restoreState = true // Removed to prevent state restoration
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}



// Alternative: Instagram-style with indicator line
 @Composable
fun InstagramStyleBottomNav(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            Icons.Outlined.Home,
            "Home"
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.CameraAlt,
            Icons.Outlined.CameraAlt,
            "Services"
        ),
        BottomNavItem(
            Screen.Gallery,
            Icons.Filled.PhotoLibrary,
            Icons.Outlined.PhotoLibrary,
            "Gallery"
        ),
        BottomNavItem(
            Screen.Pricing,
            Icons.Filled.MonetizationOn,
            Icons.Outlined.MonetizationOn,
            "Pricing"
        ),
        BottomNavItem(
            Screen.About,
            Icons.Filled.Info,
            Icons.Outlined.Info,
            "About"
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color(0xFFDBDBDB)
        )
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true

                    InstagramNavItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

 @Composable
fun InstagramNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) Color(0xFF000000) else Color(0xFF8E8E8E)

    Box(
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
            contentDescription = item.label,
            modifier = Modifier.size(28.dp),
            tint = iconColor
        )
    }
}

// YouTube-style with red accent
 @Composable
fun YouTubeStyleBottomNav(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            Icons.Outlined.Home,
            "Home"
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.Subscriptions,
            Icons.Outlined.Subscriptions,
            "Services"
        ),
        BottomNavItem(
            Screen.Gallery,
            Icons.Filled.PhotoLibrary,
            Icons.Outlined.PhotoLibrary,
            "Gallery"
        ),
        BottomNavItem(
            Screen.Pricing,
            Icons.Filled.MonetizationOn,
            Icons.Outlined.MonetizationOn,
            "Pricing"
        ),
        BottomNavItem(
            Screen.About,
            Icons.Filled.Info,
            Icons.Outlined.Info,
            "About"
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.screen.route
                } == true

                YouTubeNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

 @Composable
fun YouTubeNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF000000) else Color(0xFF606060),
        animationSpec = tween(150)
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF000000) else Color(0xFF606060),
        animationSpec = tween(150)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 10.dp, horizontal = 8.dp)
    ) {
        Icon(
            imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
            contentDescription = item.label,
            modifier = Modifier.size(24.dp),
            tint = iconColor
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = labelColor
        )
    }
}

// Professional minimal style (Recommended)
 @Composable
fun MinimalBottomNav(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            Icons.Outlined.Home,
            "Home"
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.CameraAlt,
            Icons.Outlined.CameraAlt,
            "Services"
        ),
        BottomNavItem(
            Screen.Gallery,
            Icons.Filled.PhotoLibrary,
            Icons.Outlined.PhotoLibrary,
            "Gallery"
        ),
        BottomNavItem(
            Screen.Pricing,
            Icons.Filled.MonetizationOn,
            Icons.Outlined.MonetizationOn,
            "Pricing"
        ),
        BottomNavItem(
            Screen.About,
            Icons.Filled.Info,
            Icons.Outlined.Info,
            "About"
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFE5E7EB)
        )
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true

                    MinimalNavItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

 @Composable
fun MinimalNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1F2937) else Color(0xFF9CA3AF),
        animationSpec = tween(200)
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1F2937) else Color(0xFF9CA3AF),
        animationSpec = tween(200)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 8.dp, horizontal = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
                contentDescription = item.label,
                modifier = Modifier.size(26.dp),
                tint = iconColor
            )
            
            // Bottom indicator line
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 20.dp)
                        .width(32.dp)
                        .height(2.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1F2937))
                )
            }
        }
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = item.label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = labelColor
        )
    }
}
