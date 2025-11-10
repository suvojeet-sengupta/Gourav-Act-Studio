package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvojeet.gauravactstudio.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOwnPackage(onContact: (String, String) -> Unit) {
    val customPackageTitle = stringResource(R.string.pricing_custom_package_title)
    var selectedEventType by remember { mutableStateOf("Wedding") }
    var numCameras by remember { mutableStateOf(1) }
    var numDays by remember { mutableStateOf(1) }
    var selectedVideoType by remember { mutableStateOf(VideoType.CINEMATIC) }
    var selectedAlbumSheets by remember { mutableStateOf(AlbumSheets.SHEETS_20) }

    val eventTypes = stringArrayResource(R.array.event_types).toList()
    var eventTypeExpanded by remember { mutableStateOf(false) }

    val isSpecificPackage = remember(selectedEventType, numCameras, numDays, selectedVideoType, selectedAlbumSheets) {
        (selectedEventType == "Wedding" && numCameras == 2 && numDays == 1 &&
                ((selectedAlbumSheets == AlbumSheets.SHEETS_30 && selectedVideoType == VideoType.CINEMATIC) ||
                        (selectedAlbumSheets == AlbumSheets.SHEETS_20 && selectedVideoType == VideoType.TRADITIONAL)))
    }

    val estimatedPrice = remember(selectedEventType, numCameras, numDays, selectedVideoType, selectedAlbumSheets) {
        calculateCustomPackagePrice(selectedEventType, numCameras, numDays, selectedVideoType, selectedAlbumSheets)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))           
            Text(
                text = customPackageTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp)) 
            
            Text(
                text = stringResource(R.string.pricing_custom_package_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            // Event Type Selection
            Text(
                text = "Event Type:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = eventTypeExpanded,
                onExpandedChange = { eventTypeExpanded = !eventTypeExpanded }
            ) {
                OutlinedTextField(
                    value = selectedEventType,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = eventTypeExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(16.dp)
                )
                ExposedDropdownMenu(
                    expanded = eventTypeExpanded,
                    onDismissRequest = { eventTypeExpanded = false }
                ) {
                    eventTypes.forEach { event ->
                        DropdownMenuItem(
                            text = { Text(event) },
                            onClick = {
                                selectedEventType = event
                                eventTypeExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Number of Cameras
            Text(
                text = "Number of Cameras: $numCameras",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Slider(
                value = numCameras.toFloat(),
                onValueChange = { numCameras = it.toInt() },
                valueRange = 1f..5f,
                steps = 3, // 1, 2, 3, 4, 5
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Number of Days
            Text(
                text = "Number of Days: $numDays",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Slider(
                value = numDays.toFloat(),
                onValueChange = { numDays = it.toInt() },
                valueRange = 1f..7f,
                steps = 5, // 1, 2, 3, 4, 5, 6, 7
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Video Type
            Text(
                text = "Video Type:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                VideoType.entries.forEach { type ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedVideoType = type }
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = (type == selectedVideoType),
                            onClick = { selectedVideoType = type }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = type.name.replace("_", " ").lowercase().capitalize(Locale.ROOT)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Album Sheets
            Text(
                text = "Album Sheets:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                AlbumSheets.entries.forEach { sheets ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedAlbumSheets = sheets }
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = (sheets == selectedAlbumSheets),
                            onClick = { selectedAlbumSheets = sheets }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${sheets.sheets} Sheets"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Estimated Price
            if (isSpecificPackage) {
                Text(
                    text = "Estimated Price: ₹${String.format("%,d", estimatedPrice)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            OutlinedButton(
                onClick = {
                    val details = if (isSpecificPackage) {
                        "Custom Package: Event Type - $selectedEventType, Cameras - $numCameras, Days - $numDays, Video - ${selectedVideoType.name.replace("_", " ").lowercase().capitalize(Locale.ROOT)}, Album - ${selectedAlbumSheets.sheets} Sheets. Estimated Price: ₹${String.format("%,d", estimatedPrice)}"
                    } else {
                        "Custom Package Inquiry: Event Type - $selectedEventType, Cameras - $numCameras, Days - $numDays, Video - ${selectedVideoType.name.replace("_", " ").lowercase().capitalize(Locale.ROOT)}, Album - ${selectedAlbumSheets.sheets} Sheets."
                    }
                    onContact(customPackageTitle, details)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24)))
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.pricing_contact_for_custom_quote),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

enum class VideoType {
    CINEMATIC, TRADITIONAL, BOTH
}

enum class AlbumSheets(val sheets: Int) {
    SHEETS_20(20), SHEETS_30(30), SHEETS_40(40), SHEETS_50(50)
}

fun calculateCustomPackagePrice(
    eventType: String,
    numCameras: Int,
    numDays: Int,
    videoType: VideoType,
    albumSheets: AlbumSheets
): Int {
    // Specific pricing for wedding packages as per user's request
    if (eventType == "Wedding" && numCameras == 2 && numDays == 1) {
        if (albumSheets == AlbumSheets.SHEETS_30 && videoType == VideoType.CINEMATIC) {
            return 12000
        }
        if (albumSheets == AlbumSheets.SHEETS_20 && videoType == VideoType.TRADITIONAL) {
            return 11000
        }
    }

    // General pricing for other configurations or event types
    var price = 0

    // Base price for any event
    price += when (eventType) {
        "Wedding" -> 8000 // Base for wedding, if not one of the specific packages above
        "Birthday Party" -> 2000
        "Pre-wedding" -> 5000
        else -> 1000 // Default for unknown event types
    }

    // Cameras influence (cost per camera, assuming 1 camera is included in base)
    price += (numCameras - 1) * 2000 // Cost for additional cameras

    // Days influence (cost per day, assuming 1 day is included in base)
    price += (numDays - 1) * 3000 // Cost for additional days

    // Video type influence
    price += when (videoType) {
        VideoType.CINEMATIC -> 4000
        VideoType.TRADITIONAL -> 2000
        VideoType.BOTH -> 6000
    }

    // Album sheets influence
    price += when (albumSheets) {
        AlbumSheets.SHEETS_20 -> 1500
        AlbumSheets.SHEETS_30 -> 2500
        AlbumSheets.SHEETS_40 -> 3500
        AlbumSheets.SHEETS_50 -> 4500
    }

    // Ensure minimum price is not too low
    return maxOf(price, 5000)
}
