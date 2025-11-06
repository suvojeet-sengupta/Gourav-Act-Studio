package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.R

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String,
    val gradient: List<Color>
)

 @Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            "Home",
            listOf(Color(0xFFEC4899), Color(0xFFF97316))
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.PhotoCamera,
            "Services",
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
        ),
        BottomNavItem(
            Screen.Portfolio,
            Icons.Filled.Collections,
            "Portfolio",
            listOf(Color(0xFF10B981), Color(0xFF14B8A6))
        ),
        BottomNavItem(
            Screen.Contact,
            Icons.Filled.ContactMail,
            "Contact",
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
        ),
        BottomNavItem(
            Screen.Settings,
            Icons.Filled.Settings,
            "Settings",
            listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        tonalElevation = 12.dp,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.screen.route
                } == true

                BottomNavItemView(
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
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Animation for scale effect
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.0f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // Animation for icon size
    val iconSize by animateDpAsState(
        targetValue = if (isSelected) 28.dp else 24.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    // Animation for container size
    val containerSize by animateDpAsState(
        targetValue = if (isSelected) 64.dp else 52.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .selectable(
                selected = isSelected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(containerSize)
                .clip(RoundedCornerShape(18.dp))
                .background(
                    if (isSelected) {
                        Brush.linearGradient(item.gradient)
                    } else {
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFF3F4F6),
                                Color(0xFFE5E7EB)
                            )
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                modifier = Modifier.size(iconSize),
                tint = if (isSelected) Color.White else Color(0xFF6B7280)
            )
        }

        // Label and indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(300)) +
                    expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(200)) +
                   shrinkVertically(animationSpec = tween(200))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Text(
                    text = item.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(item.gradient)
                        )
                )
            }
        }
    }
}

// Alternative: Floating Style Bottom Navigation
 @Composable
fun FloatingBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            "Home",
            listOf(Color(0xFFEC4899), Color(0xFFF97316))
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.PhotoCamera,
            "Services",
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
        ),
        BottomNavItem(
            Screen.Portfolio,
            Icons.Filled.Collections,
            "Portfolio",
            listOf(Color(0xFF10B981), Color(0xFF14B8A6))
        ),
        BottomNavItem(
            Screen.Contact,
            Icons.Filled.ContactMail,
            "Contact",
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
        ),
        BottomNavItem(
            Screen.Settings,
            Icons.Filled.Settings,
            "Settings",
            listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            shadowElevation = 20.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true

                    FloatingNavItemView(
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
fun FloatingNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Pulsing animation for selected item
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val scale = if (isSelected) pulse else 1f

    Surface(
        onClick = onClick,
        modifier = Modifier.scale(scale),
        shape = RoundedCornerShape(22.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 68.dp else 56.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(
                    if (isSelected) {
                        Brush.linearGradient(item.gradient)
                    } else {
                        Brush.linearGradient(
                            listOf(Color(0xFFF9FAFB), Color(0xFFF3F4F6))
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.size(if (isSelected) 32.dp else 26.dp),
                    tint = if (isSelected) Color.White else Color(0xFF6B7280)
                )
                
                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Alternative: Minimal Pill Style
 @Composable
fun PillBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            Screen.Home,
            Icons.Filled.Home,
            "Home",
            listOf(Color(0xFFEC4899), Color(0xFFF97316))
        ),
        BottomNavItem(
            Screen.Services,
            Icons.Filled.PhotoCamera,
            "Services",
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
        ),
        BottomNavItem(
            Screen.Portfolio,
            Icons.Filled.Collections,
            "Portfolio",
            listOf(Color(0xFF10B981), Color(0xFF14B8A6))
        ),
        BottomNavItem(
            Screen.Contact,
            Icons.Filled.ContactMail,
            "Contact",
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
        ),
        BottomNavItem(
            Screen.Settings,
            Icons.Filled.Settings,
            "Settings",
            listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        tonalElevation = 8.dp,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.screen.route
                } == true

                PillNavItemView(
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
fun PillNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) 80.dp else 52.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(26.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(
                    if (isSelected) {
                        Brush.horizontalGradient(item.gradient)
                    } else {
                        Brush.linearGradient(
                            listOf(Color(0xFFF9FAFB), Color(0xFFF3F4F6))
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) Color.White else Color(0xFF6B7280)
                )
                
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}