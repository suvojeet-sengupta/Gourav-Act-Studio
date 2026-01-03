package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
            Icons.Filled.CurrencyRupee,
            Icons.Outlined.CurrencyRupee,
            "Pricing"
        ),
        BottomNavItem(
            Screen.About,
            Icons.Filled.Info,
            Icons.Outlined.Info,
            "About"
        )
    )

    // Floating-style Card container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp, // High elevation for that "floating" look
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Better spacing for 5 items
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.screen.route
                } == true

                BottomNavItemContent(
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
fun BottomNavItemContent(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Animate color for smooth transition
    val activeColor = Color(0xFFEC4899) // Pink to match Home theme
    val inactiveColor = Color(0xFF9CA3AF)

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else inactiveColor,
        animationSpec = tween(300),
        label = "Icon Color"
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else inactiveColor,
        animationSpec = tween(300),
        label = "Label Color"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Remove ripple for cleaner look, or keep it if preferred
            )
            .padding(8.dp)
            .weight(1f) // Distribute click area evenly
    ) {
        // Icon Box
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Optional: Add a subtle background indicator for selected state if desired, 
            // but Swiggy/Zomato usually just use bold/color. 
            // We will stick to the bold icon + color.
            
            // Top Indicator (Swiggy style often has a top bar, let's add a small one above or just rely on the icon)
            // For this design, let's keep it clean with just the Icon and Text scaling slightly.
            
            Icon(
                imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
                contentDescription = item.label,
                modifier = Modifier.size(24.dp),
                tint = iconColor
            )
            
            if (isSelected) {
                // Top border indicator line - positioned at the very top of the item or just below icon?
                // Visual choice: Let's put a small dash BELOW the text or effectively just use color.
                // The user asked for "Swiggy/Zomato like".
                // Zomato uses a thick top border. Let's try to add a top border to the whole item 
                // but that requires measuring correctly. 
                // Simplest high-quality look: Color + Bold Text + maybe a glow?
                // Let's stick to simple Color + Bold.
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = labelColor,
            maxLines = 1
        )
    }
}