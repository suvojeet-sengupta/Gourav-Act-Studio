package com.suvojeet.gauravactstudio.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    isSuccess: Boolean = false,
    bookingRequestNumber: String? = null,
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

    // Date/Time States
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    // Location
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasLocationPermission = it }
    )

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0]
                            location = "${address.locality ?: ""}, ${address.adminArea ?: ""}".trim(',',' ')
                        }
                    } catch (e: IOException) {}
                }
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    // Validation
    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var eventTypeError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    val validate = {
        nameError = name.isBlank()
        phoneError = phone.length != 10
        eventTypeError = eventType.isBlank()
        dateError = date.isBlank()
        !nameError && !phoneError && !eventTypeError && !dateError
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
                        date = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
                        dateError = false
                    }
                }) { Text("OK") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showTimePicker = false
                    eventTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                }) { Text("OK") }
            },
            title = { Text("Select time") },
            // Fixed: Title is a lambda in Material3 TimePickerDialog
            content = { TimePicker(state = timePickerState) }
        )
    }

    ModalBottomSheet(
        onDismissRequest = { if (!isSubmitting) onDismiss() },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White
    ) {
        AnimatedContent(targetState = isSuccess, label = "SuccessTransition") { success ->
            if (success) {
                BookingSuccessReceipt(
                    bookingRef = bookingRequestNumber ?: "N/A",
                    name = name,
                    phone = phone,
                    eventType = if (eventType == "Other") otherEventType else eventType,
                    date = date,
                    time = eventTime,
                    location = location,
                    onDone = onDismiss
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.booking_header_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = "Booking for: $packageName",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFEC4899),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Personal Info Section
                    BookingSectionHeader(stringResource(R.string.booking_personal_info))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ModernTextField(
                                value = name,
                                onValueChange = { name = it; nameError = false },
                                label = stringResource(R.string.booking_full_name_label),
                                isError = nameError,
                                icon = Icons.Outlined.Person
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            ModernTextField(
                                value = phone,
                                onValueChange = { if(it.length <= 10) phone = it; phoneError = false },
                                label = stringResource(R.string.booking_phone_number_label),
                                isError = phoneError,
                                icon = Icons.Outlined.Phone,
                                keyboardType = KeyboardType.Phone
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Event Details Section
                    BookingSectionHeader(stringResource(R.string.booking_event_info))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Event Type Dropdown (Simplified display)
                            Box(modifier = Modifier.fillMaxWidth()) {
                                ModernTextField(
                                    value = eventType,
                                    onValueChange = {},
                                    label = stringResource(R.string.booking_event_type_label),
                                    isError = eventTypeError,
                                    icon = Icons.Outlined.Event,
                                    readOnly = true,
                                    trailingIcon = { Icon(Icons.Filled.ArrowDropDown, null) }
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable { expanded = true }
                                )
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    eventTypes.forEach { type ->
                                        DropdownMenuItem(text = { Text(type) }, onClick = { eventType = type; expanded = false; eventTypeError = false })
                                    }
                                }
                            }
                            
                            if(eventType == "Other") {
                                 Spacer(modifier = Modifier.height(16.dp))
                                 ModernTextField(
                                    value = otherEventType,
                                    onValueChange = { otherEventType = it },
                                    label = "Specify Event",
                                    icon = Icons.Outlined.Edit
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(modifier = Modifier.weight(1f)) {
                                    ModernTextField(value = date, onValueChange = {}, label = "Date", icon = Icons.Outlined.CalendarToday, readOnly = true, isError = dateError)
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clickable { showDatePicker = true }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    ModernTextField(value = eventTime, onValueChange = {}, label = "Time", icon = Icons.Outlined.AccessTime, readOnly = true)
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clickable { showTimePicker = true }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ModernTextField(
                                value = eventAddress,
                                onValueChange = { eventAddress = it },
                                label = "Venue Address",
                                icon = Icons.Outlined.LocationOn
                            )
                        }
                    }
                }
            }
        }

        // Sticky Submit Button
        if(!isSuccess) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 16.dp
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { if(validate()) onSubmit(name, phone, eventType, otherEventType, date, eventTime, eventAddress, notes, location) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2937)),
                        enabled = !isSubmitting
                    ) {
                        if(isSubmitting) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        else Text(stringResource(R.string.booking_submit), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF6B7280),
        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
    )
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector, // Fixed: removed full qualification
    isError: Boolean = false,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color(0xFF374151), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            prefix = { Icon(icon, null, modifier = Modifier.size(18.dp), tint = Color(0xFF9CA3AF)); Spacer(Modifier.width(8.dp)) },
            shape = RoundedCornerShape(10.dp),
            readOnly = readOnly,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors( // Fixed colors
                focusedBorderColor = Color(0xFFEC4899),
                unfocusedBorderColor = Color(0xFFE5E7EB),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            trailingIcon = trailingIcon,
            singleLine = true
        )
    }
}

// Keep Success Receipt and other helpers...
@Composable
fun BookingSuccessReceipt(
    bookingRef: String,
    name: String,
    phone: String,
    eventType: String,
    date: String,
    time: String,
    location: String,
    onDone: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF10B981), modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(16.dp))
        Text("Request Sent!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Reference: $bookingRef", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
        
        Spacer(Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ReceiptRow("Name", name)
                ReceiptRow("Event", eventType)
                ReceiptRow("Date", "$date at $time")
            }
        }
        
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Done", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color(0xFF6B7280))
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}