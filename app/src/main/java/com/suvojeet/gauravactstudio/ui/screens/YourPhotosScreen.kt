package com.suvojeet.gauravactstudio.ui.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.widget.DatePicker
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
import com.suvojeet.gauravactstudio.ui.components.LightDecorativeBackground
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay
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
    val formattedWhatsappNumber by remember(whatsappNumber) {
        derivedStateOf {
            whatsappNumber.chunked(5).joinToString(" ")
        }
    }
    var eventDate by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var isVisible by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var whatsappNumberError by remember { mutableStateOf(false) }
    var whatsappNumberLengthError by remember { mutableStateOf(false) }
    var eventDateError by remember { mutableStateOf(false) }
    var eventTypeError by remember { mutableStateOf(false) }

    val validateForm = {
        nameError = name.isBlank()
        whatsappNumberError = whatsappNumber.isBlank()
        whatsappNumberLengthError = whatsappNumber.isNotBlank() && whatsappNumber.length < 10
        eventDateError = eventDate.isBlank()
        eventTypeError = eventType.isBlank()

        !nameError && !whatsappNumberError && !whatsappNumberLengthError && !eventDateError && !eventTypeError
    }

    val context = LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    var showDatePicker by remember { mutableStateOf(false) }

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

    if (uiState.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            title = { Text("Request Sent") },
            text = { Text("Your request for photos has been sent successfully. You will get contacted from 9354654066 or you can directly request via whatsapp too.") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://api.whatsapp.com/send?phone=919354654066")
                            }
                            context.startActivity(intent)
                            viewModel.dismissSuccessDialog()
                        }
                    ) {
                        Text("WhatsApp")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { viewModel.dismissSuccessDialog() }) {
                        Text("OK")
                    }
                }
            }
        )
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirm Your Details") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Please review your details before submitting:")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Name: $name", fontWeight = FontWeight.Bold)
                    Text("WhatsApp Number: $whatsappNumber", fontWeight = FontWeight.Bold)
                    Text("Event Date: $eventDate", fontWeight = FontWeight.Bold)
                    Text("Event Type: $eventType", fontWeight = FontWeight.Bold)
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
                    }
                ) {
                    Text("Confirm & Send")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showConfirmationDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFFFFBFE),
                        Color(0xFFF5F5F7)
                    )
                )
            )
    ) {
        LightDecorativeBackground(scrollState.value)

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Get Your Photos") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    windowInsets = WindowInsets(0.dp)
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
                AnimatedContent(isVisible) {
                    Icon(
                        imageVector = Icons.Filled.CloudDownload,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedContent(isVisible, delay = 200) {
                    Text(
                        text = "Fill in the details below to request your event photos.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = MaterialTheme.shapes.large,
                            ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it; nameError = false },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isSubmitting,
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            isError = nameError,
                            supportingText = {
                                if (nameError) {
                                    Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            trailingIcon = {
                                if (nameError) {
                                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        )
                        OutlinedTextField(
                            value = formattedWhatsappNumber,
                            onValueChange = {
                                val newText = it.filter { char -> char.isDigit() }
                                if (newText.length <= 10) {
                                    if (newText.isEmpty() || (newText.isNotEmpty() && newText.first() in listOf('9', '8', '7', '6'))) {
                                        whatsappNumber = newText
                                        whatsappNumberError = false
                                        whatsappNumberLengthError = false
                                    }
                                }
                            },
                            label = { Text("WhatsApp Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isSubmitting,
                            shape = RoundedCornerShape(16.dp),
                            isError = whatsappNumberError || whatsappNumberLengthError,
                            supportingText = {
                                if (whatsappNumberError) {
                                    Text("WhatsApp Number cannot be empty", color = MaterialTheme.colorScheme.error)
                                } else if (whatsappNumberLengthError) {
                                    Text("WhatsApp Number must be 10 digits", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            trailingIcon = {
                                if (whatsappNumberError || whatsappNumberLengthError) {
                                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        )
                        OutlinedTextField(
                            value = eventDate,
                            onValueChange = { },
                            label = { Text("Event Date (e.g., DD-MM-YYYY)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePicker = true },
                            enabled = false,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = if (eventDateError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            isError = eventDateError,
                            supportingText = {
                                if (eventDateError) {
                                    Text("Event Date cannot be empty", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            trailingIcon = {
                                if (eventDateError) {
                                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        )
                        OutlinedTextField(
                            value = eventType,
                            onValueChange = { eventType = it; eventTypeError = false },
                            label = { Text("Event Type (e.g., Wedding, Birthday)") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isSubmitting,
                            shape = RoundedCornerShape(16.dp),
                            isError = eventTypeError,
                            supportingText = {
                                if (eventTypeError) {
                                    Text("Event Type cannot be empty", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            trailingIcon = {
                                if (eventTypeError) {
                                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (validateForm()) {
                                    showConfirmationDialog = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !uiState.isSubmitting,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (uiState.isSubmitting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text(
                                        "Submit Request",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.Bold
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
