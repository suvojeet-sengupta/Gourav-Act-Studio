package com.suvojeet.gauravactstudio.ui.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.widget.DatePicker
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.suvojeet.gauravactstudio.ui.components.ModernTextField
import com.suvojeet.gauravactstudio.ui.components.ReceiptRow
import com.suvojeet.gauravactstudio.ui.components.BookingSectionHeader
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourPhotosScreen(
    navController: NavController,
    viewModel: YourPhotosViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var whatsappNumber by remember { mutableStateOf("") }
    // Remove the formattedWhatsappNumber derived state as ModernTextField handles it cleanly, or we can keep it if needed. 
    // Simplified input handling for now.

    var eventDate by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var isVisible by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var whatsappNumberError by remember { mutableStateOf(false) }
    var eventDateError by remember { mutableStateOf(false) }
    var eventTypeError by remember { mutableStateOf(false) }

    val validateForm = {
        nameError = name.isBlank()
        whatsappNumberError = whatsappNumber.length != 10
        eventDateError = eventDate.isBlank()
        eventTypeError = eventType.isBlank()

        !nameError && !whatsappNumberError && !eventDateError && !eventTypeError
    }

    val context = LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    
    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(uiState.showSuccessDialog) {
        if (uiState.showSuccessDialog) {
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance().apply { timeInMillis = millis }
                            val day = calendar.get(Calendar.DAY_OF_MONTH)
                            val month = calendar.get(Calendar.MONTH) + 1
                            val year = calendar.get(Calendar.YEAR)
                            eventDate = "$day/$month/$year"
                            eventDateError = false
                        }
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LaunchedEffect(Unit) {
        delay(50)
        isVisible = true
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.onSnackbarShown()
        }
    }

    // Reuse BookingSuccessReceipt logic or keep existing dialog but styled better?
    // Let's use the existing logic but maybe we can make it look like the BookingSuccessReceipt if we want consistency.
    // However, the original code had a specific whatsapp intent. I will keep the original logic but improve the dialog if needed
    // Actually, let's keep the logic exactly as is for the success dialog to ensure no regression in behavior, 
    // but maybe just clean up the code.
    
    if (uiState.showSuccessDialog) {
        // ... (Keep existing success dialog logic for now, or replace with BookingSuccessReceipt if appropriate)
        // The user want's it to look like Home Screen or Book Now. Book Now uses BookingSuccessReceipt.
        // But formatting might differ. Let's stick to the functional logic of the original for now but maybe wrap it differently?
        // Actually, the original success dialog is an AlertDialog. Let's keep it an AlertDialog but maybe style the inner text?
        // Or if I really want to impress, I should use the receipt. 
        // But the receipt is a full screen view in the bottom sheet. Here we are in a screen. 
        // Let's use a nice AlertDialog as before, but maybe cleaner. 
        
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            icon = { Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF10B981)) },
            title = { Text("Request Sent Successfully") },
            text = { Text("Your photo request has been received. We will contact you at $whatsappNumber shortly. You can also message us directly on WhatsApp.") },
            confirmButton = {
                 TextButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?phone=919354654066")
                        }
                        context.startActivity(intent)
                        viewModel.dismissSuccessDialog()
                    }
                ) { Text("Chat on WhatsApp") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissSuccessDialog() }) { Text("Done") }
            }
        )
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirm Request") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                     ReceiptRow("Name", name)
                     ReceiptRow("WhatsApp", whatsappNumber)
                     ReceiptRow("Date", eventDate)
                     ReceiptRow("Event", eventType)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog = false
                        viewModel.submitRequest(
                            name = name,
                            whatsappNumber = whatsappNumber,
                            eventDate = eventDate,
                            eventType = eventType
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Confirm & Send")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showConfirmationDialog = false }) { Text("Cancel") }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F5F9)) // Match Home Screen background
    ) {
        // Decorative background if needed, but HomeScreen is cleaner.
        // LightDecorativeBackground(scrollState.value) 

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
               // Use a simple top app bar or custom header
               TopAppBar(
                   title = { 
                       Text(
                           "Get Your Photos", 
                           fontWeight = FontWeight.Bold,
                           style = MaterialTheme.typography.titleMedium
                       ) 
                   },
                   navigationIcon = {
                       IconButton(onClick = { navController.popBackStack() }) {
                           Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                       }
                   },
                   colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
               )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                
                // Header Icon
                AnimatedContent(isVisible) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F2FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CloudDownload,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color(0xFF0EA5E9)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                AnimatedContent(isVisible, delay = 100) {
                     Text(
                        text = "Retrieve your event photos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        textAlign = TextAlign.Center
                    )
                }
                 AnimatedContent(isVisible, delay = 150) {
                     Text(
                        text = "Enter your details below to find your collection",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Modern Form Card
                AnimatedContent(isVisible, delay = 200) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                             BookingSectionHeader("Personal Details")
                             ModernTextField(
                                value = name,
                                onValueChange = { name = it; nameError = false },
                                label = "Full Name",
                                icon = Icons.Outlined.Person,
                                isError = nameError
                            )
                            
                            ModernTextField(
                                value = whatsappNumber,
                                onValueChange = { 
                                    if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                        whatsappNumber = it
                                        whatsappNumberError = false
                                    }
                                },
                                label = "WhatsApp Number",
                                icon = Icons.Outlined.Phone, // Use Phone icon
                                isError = whatsappNumberError,
                                keyboardType = KeyboardType.Phone
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            BookingSectionHeader("Event Information")

                            // Date Field with click
                            Box {
                                ModernTextField(
                                    value = eventDate,
                                    onValueChange = {},
                                    label = "Event Date",
                                    icon = Icons.Outlined.CalendarToday,
                                    isError = eventDateError,
                                    readOnly = true
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable { showDatePicker = true }
                                )
                            }

                            ModernTextField(
                                value = eventType,
                                onValueChange = { eventType = it; eventTypeError = false },
                                label = "Event Type (e.g. Wedding)",
                                icon = Icons.Outlined.Event,
                                isError = eventTypeError
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Button(
                                onClick = {
                                    if (validateForm()) {
                                        showConfirmationDialog = true
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(14.dp),
                                enabled = !uiState.isSubmitting,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0EA5E9), // Match theme
                                    disabledContainerColor = Color(0xFFE2E8F0)
                                )
                            ) {
                                if (uiState.isSubmitting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                    )
                                } else {
                                    Text(
                                        "Find Photos",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourPhotosScreenPreview() {
    GauravActStudioTheme {
        YourPhotosScreen(navController = rememberNavController())
    }
}
