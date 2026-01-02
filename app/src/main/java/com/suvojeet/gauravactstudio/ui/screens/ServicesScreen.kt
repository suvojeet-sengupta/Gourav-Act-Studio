package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme

data class Service(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
    val isHighlighted: Boolean = false,
    val category: String = "All" 
)

@Composable
fun ServicesScreen(navController: NavController) {
    // Data (Using existing strings)
    val allServices = listOf(
        Service(
            stringResource(R.string.service_wedding_photography_title),
            stringResource(R.string.service_wedding_photography_description),
            Icons.Filled.Camera,
            listOf(Color(0xFFEC4899), Color(0xFFF97316)),
            category = "Wedding"
        ),
        Service(
            stringResource(R.string.service_ring_ceremony_title),
            stringResource(R.string.service_ring_ceremony_description),
            Icons.Filled.Favorite,
            listOf(Color(0xFFEF4444), Color(0xFFF59E0B)),
            category = "Wedding"
        ),
        Service(
            stringResource(R.string.service_birthday_celebrations_title),
            stringResource(R.string.service_birthday_celebrations_description),
            Icons.Filled.CardGiftcard,
            listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
            category = "Events"
        ),
        Service(
            stringResource(R.string.service_pre_wedding_shoots_title),
            stringResource(R.string.service_pre_wedding_shoots_description),
            Icons.Filled.PhotoCamera,
            listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)),
            category = "Wedding"
        ),
        Service(
            stringResource(R.string.service_maternity_shoots_title),
            stringResource(R.string.service_maternity_shoots_description),
            Icons.Filled.ChildFriendly,
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24)),
             category = "Events"
        ),
        Service(
            stringResource(R.string.service_baby_shoots_title),
            stringResource(R.string.service_baby_shoots_description),
            Icons.Filled.ChildFriendly,
            listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
             category = "Events"
        ),
        Service(
            stringResource(R.string.service_corporate_events_title),
            stringResource(R.string.service_corporate_events_description),
            Icons.Filled.CorporateFare,
            listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
            category = "Corporate"
        ),
        Service(
            stringResource(R.string.service_fashion_portfolio_title),
            stringResource(R.string.service_fashion_portfolio_description),
            Icons.Filled.Style,
            listOf(Color(0xFFEC4899), Color(0xFFA855F7)),
            category = "Corporate"
        ),
        Service(
            stringResource(R.string.service_product_photography_title),
            stringResource(R.string.service_product_photography_description),
            Icons.Filled.Videocam,
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
             category = "Corporate"
        ),
        Service(
            stringResource(R.string.service_wedding_card_design_title),
            stringResource(R.string.service_wedding_card_design_description),
            Icons.Filled.Create,
            listOf(Color(0xFFF97316), Color(0xFFFBBF24)),
            category = "Wedding"
        ),
        Service(
            stringResource(R.string.service_business_promotion_shoots_title),
            stringResource(R.string.service_business_promotion_shoots_description),
            Icons.Filled.Store,
            listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)),
            isHighlighted = true,
             category = "Corporate"
        )
    )

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredServices = allServices.filter { service ->
        (selectedCategory == "All" || service.category == selectedCategory) &&
        (service.title.contains(searchQuery, ignoreCase = true) || 
         service.description.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F5F9))
    ) {
        // Sticky Header / Top Bar
        Surface(
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                 Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.services_header_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                }
                
                // Search Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF9CA3AF)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // Note: Using BasicTextField for custom look would be better, but Text is fine for display
                         Text(
                            text = if(searchQuery.isEmpty()) stringResource(R.string.services_search_placeholder) else searchQuery,
                            color = if(searchQuery.isEmpty()) Color(0xFF9CA3AF) else Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Categories Row
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterChip(
                        selected = selectedCategory == "All",
                        onClick = { selectedCategory = "All" },
                        label = stringResource(R.string.services_category_all)
                    )
                    FilterChip(
                        selected = selectedCategory == "Wedding",
                        onClick = { selectedCategory = "Wedding" },
                        label = stringResource(R.string.services_category_wedding)
                    )
                    FilterChip(
                        selected = selectedCategory == "Events",
                        onClick = { selectedCategory = "Events" },
                        label = stringResource(R.string.services_category_events)
                    )
                     FilterChip(
                        selected = selectedCategory == "Corporate",
                        onClick = { selectedCategory = "Corporate" },
                        label = stringResource(R.string.services_category_corporate)
                    )
                }
            }
        }

        // List Content
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredServices) { service ->
                ModernServiceItem(service = service, navController = navController)
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp)) // Bottom padding
            }
        }
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Surface(
        color = if (selected) Color(0xFF1F2937) else Color.White,
        contentColor = if (selected) Color.White else Color(0xFF1F2937),
        shape = RoundedCornerShape(20.dp),
        border = if (!selected) BorderStroke(1.dp, Color(0xFFE5E7EB)) else null,
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(36.dp),
        shadowElevation = if (selected) 4.dp else 0.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ModernServiceItem(service: Service, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable { navController.navigate(Screen.Pricing.route) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon / Image Placeholder on Left
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.linearGradient(service.gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = service.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.weight(1f)
                    )
                    if (service.isHighlighted) {
                         Box(
                            modifier = Modifier
                                .background(Color(0xFFFFF7ED), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFF97316), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "POPULAR",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFF97316),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.services_action_check_prices),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFEC4899),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = Color(0xFFEC4899),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    GauravActStudioTheme {
        ServicesScreen(navController = rememberNavController())
    }
}