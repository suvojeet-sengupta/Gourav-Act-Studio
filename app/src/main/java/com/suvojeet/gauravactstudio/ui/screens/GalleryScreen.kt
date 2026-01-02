package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.screens.gallery.PhotosScreen
import com.suvojeet.gauravactstudio.ui.screens.gallery.VideosScreen

@Composable
fun GalleryScreen(navController: NavController, modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Photos", "Videos")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F5F9))
    ) {
        // Header
        Surface(
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.gallery_header_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                }

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.White,
                    contentColor = Color(0xFF1F2937),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color(0xFFEC4899)
                        )
                    },
                    divider = { HorizontalDivider(color = Color(0xFFF3F4F6)) }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { 
                                Text(
                                    text = title,
                                    fontWeight = if(selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium
                                ) 
                            }
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {
                0 -> PhotosScreen(navController = navController)
                1 -> VideosScreen(navController = navController)
            }
        }
    }
}