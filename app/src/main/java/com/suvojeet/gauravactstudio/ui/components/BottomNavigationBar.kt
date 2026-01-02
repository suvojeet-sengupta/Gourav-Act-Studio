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
}

@Composable
fun BottomNavItemContent(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1F2937) else Color(0xFF9CA3AF),
        animationSpec = tween(200),
        label = "Icon Color"
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1F2937) else Color(0xFF9CA3AF),
        animationSpec = tween(200),
        label = "Label Color"
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