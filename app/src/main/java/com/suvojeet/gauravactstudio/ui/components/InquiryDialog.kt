package com.suvojeet.gauravactstudio.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.suvojeet.gauravactstudio.R
import java.io.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDialog(
    packageName: String,
    isSubmitting: Boolean,
    isSuccess: Boolean = false, // New parameter
    onDismiss: () -> Unit,
    onSubmit: (
        name: String,
        phone: String,
        eventType: String,
        otherEventType: String,
        date: String,
        eventTime: String,
        eventAddress: String,
        notes: String,
        location: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }
    var otherEventType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventAddress by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Unknown location") }

    val eventTypes = stringArrayResource(R.array.event_types)
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Time Picker State
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    // Location
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasLocationPermission = isGranted
        }
    )

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    if (loc != null) {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        try {
                            val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                            if (addresses != null && addresses.isNotEmpty()) {
                                val address = addresses[0]
                                val city = address.locality
                                val state = address.adminArea
                                if(city != null && state != null){
                                    location = "$city, $state"
                                } else {
                                    location = "Location not found"
                                }
                            }
                        } catch (e: IOException) {
                            location = "Could not get location"
                        }
                    }
                }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    // Validation states
    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var phoneLengthError by remember { mutableStateOf(false) }
    var eventTypeError by remember { mutableStateOf(false) }
    var otherEventTypeRequiredError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var invalidDateError by remember { mutableStateOf(false) }
    var eventTimeError by remember { mutableStateOf(false) }
    var eventAddressError by remember { mutableStateOf(false) }

    val validateForm: () -> Boolean = {
        nameError = name.isBlank()
        phoneError = phone.isBlank()
        phoneLengthError = phone.length != 10
        eventTypeError = eventType.isBlank()
        otherEventTypeRequiredError = eventType == "Other" && otherEventType.isBlank()
        dateError = date.isBlank()
        eventTimeError = eventTime.isBlank()
        eventAddressError = eventAddress.isBlank()

        !nameError && !phoneError && !phoneLengthError && !eventTypeError && !otherEventTypeRequiredError && !dateError && !invalidDateError && !eventTimeError && !eventAddressError
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
                            val today = LocalDate.now(ZoneId.systemDefault())
                            if (localDate < today) {
                                invalidDateError = true
                                date = ""
                            } else {
                                invalidDateError = false
                                date = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
                                dateError = false
                            }
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

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                        eventTime = "${timePickerState.hour}:${timePickerState.minute}"
                        eventTimeError = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            title = { Text("Select Event Time") }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    ModalBottomSheet(
        onDismissRequest = { if (!isSubmitting) onDismiss() },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) } // Allow full screen height use
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp) // Extra padding for sticky bottom bar
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    // ... (Header Content remains same)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EditCalendar,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = stringResource(R.string.booking_send_request),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = packageName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Name Field
                FormField(
                    icon = Icons.Filled.Person,
                    label = stringResource(R.string.booking_full_name_label)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it; nameError = false },
                        placeholder = { Text(stringResource(R.string.booking_enter_full_name_placeholder)) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        ),
                        singleLine = true,
                        isError = nameError,
                        supportingText = {
                            if (nameError) {
                                Text(stringResource(R.string.booking_name_empty_error), color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Phone Field
                FormField(
                    icon = Icons.Filled.Phone,
                    label = stringResource(R.string.booking_phone_number_label)
                ) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.length <= 10) {
                                phone = it
                                phoneError = false
                                phoneLengthError = false
                            } else {
                                phoneLengthError = true
                            }
                        },
                        placeholder = { Text(stringResource(R.string.booking_enter_phone_placeholder)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        ),
                        singleLine = true,
                        isError = phoneError || phoneLengthError,
                        supportingText = {
                            if (phoneError) {
                                Text(stringResource(R.string.booking_phone_empty_error), color = MaterialTheme.colorScheme.error)
                            } else if (phoneLengthError) {
                                Text(stringResource(R.string.booking_phone_length_error), color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Event Type Field
                FormField(
                    icon = Icons.Filled.Event,
                    label = stringResource(R.string.booking_event_type_label)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { if (!isSubmitting) expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = eventType,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text(stringResource(R.string.booking_select_event_type_placeholder)) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            enabled = !isSubmitting,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            isError = eventTypeError,
                            supportingText = {
                                if (eventTypeError) {
                                    Text(stringResource(R.string.booking_event_type_empty_error), color = MaterialTheme.colorScheme.error)
                                }
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            eventTypes.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        eventType = selectionOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Other Event Type (conditional)
                AnimatedVisibility(
                    visible = eventType == "Other",
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        FormField(
                            icon = Icons.Filled.Edit,
                            label = stringResource(R.string.booking_specify_event_type_label)
                        ) {
                            OutlinedTextField(
                                value = otherEventType,
                                onValueChange = { otherEventType = it; otherEventTypeRequiredError = false },
                                placeholder = { Text(stringResource(R.string.booking_specify_placeholder)) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isSubmitting,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                ),
                                singleLine = true,
                                isError = otherEventTypeRequiredError,
                                supportingText = {
                                    if (otherEventTypeRequiredError) {
                                        Text(stringResource(R.string.booking_specify_event_type_error), color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date & Time Row
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                                            FormField(
                                                icon = Icons.Filled.DateRange,
                                                label = "Date"
                                            ) {
                                                Box(modifier = Modifier.fillMaxWidth()) {
                                                    OutlinedTextField(
                                                        value = date,
                                                        onValueChange = { date = it; dateError = false },
                                                        readOnly = true,
                                                        placeholder = { Text("Select") },
                                                        modifier = Modifier.fillMaxWidth(),
                                                        enabled = !isSubmitting,
                                                        shape = RoundedCornerShape(12.dp),
                                                        colors = OutlinedTextFieldDefaults.colors(
                                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                                            unfocusedBorderColor = Color(0xFFE0E0E0)
                                                        ),
                                                        isError = dateError || invalidDateError,
                                                        supportingText = {
                                                            if (dateError) Text("Required", color = MaterialTheme.colorScheme.error)
                                                        }
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .matchParentSize()
                                                            .clickable(enabled = !isSubmitting) { showDatePicker = true }
                                                    )
                                                }
                                            }                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Box(modifier = Modifier.weight(1f)) {
                                            FormField(
                                                icon = Icons.Filled.Schedule,
                                                label = "Time"
                                            ) {
                                                Box(modifier = Modifier.fillMaxWidth()) {
                                                    OutlinedTextField(
                                                        value = eventTime,
                                                        onValueChange = { eventTime = it; eventTimeError = false },
                                                        readOnly = true,
                                                        placeholder = { Text("Select") },
                                                        modifier = Modifier.fillMaxWidth(),
                                                        enabled = !isSubmitting,
                                                        shape = RoundedCornerShape(12.dp),
                                                        colors = OutlinedTextFieldDefaults.colors(
                                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                                            unfocusedBorderColor = Color(0xFFE0E0E0)
                                                        ),
                                                        isError = eventTimeError,
                                                        supportingText = {
                                                            if (eventTimeError) Text("Required", color = MaterialTheme.colorScheme.error)
                                                        }
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .matchParentSize()
                                                            .clickable(enabled = !isSubmitting) { showTimePicker = true }
                                                    )
                                                }
                                            }                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Event Address Field
                FormField(
                    icon = Icons.Filled.LocationOn,
                    label = "Event Address"
                ) {
                    OutlinedTextField(
                        value = eventAddress,
                        onValueChange = { eventAddress = it; eventAddressError = false },
                        placeholder = { Text("Enter event address") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        ),
                        singleLine = true,
                        isError = eventAddressError,
                        supportingText = {
                            if (eventAddressError) {
                                Text("Event address cannot be empty", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Notes Field
                FormField(
                    icon = Icons.Filled.Notes,
                    label = stringResource(R.string.booking_additional_requirements_label)
                ) {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text(stringResource(R.string.booking_notes_placeholder)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        maxLines = 4,
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        )
                    )
                }
            }
            
            // Sticky Bottom Bar
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shadowElevation = 16.dp,
                color = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(bottom = 16.dp) // Extra bottom padding for gestures
                ) {
                    Button(
                        onClick = {
                            if (validateForm() && !isSuccess) {
                                onSubmit(name, phone, eventType, otherEventType, date, eventTime, eventAddress, notes, location)
                            }
                        },
                        enabled = !isSubmitting && !isSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSuccess) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                            disabledContainerColor = if (isSuccess) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp
                        )
                    ) {
                        AnimatedContent(
                            targetState = when {
                                isSuccess -> "Success"
                                isSubmitting -> "Submitting"
                                else -> "Idle"
                            },
                            label = "Submit Button Animation"
                        ) { state ->
                            when (state) {
                                "Submitting" -> {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(R.string.booking_sending),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                                "Success" -> {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Request Sent!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                                else -> {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Send,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(R.string.booking_submit),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FormField(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        content()
    }
}