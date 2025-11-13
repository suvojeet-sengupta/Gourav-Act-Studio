package com.suvojeet.gauravactstudio.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvojeet.gauravactstudio.util.EmailService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class YourPhotosScreenState(
    val isSubmitting: Boolean = false,
    val snackbarMessage: String? = null,
    val showSuccessDialog: Boolean = false
)

class YourPhotosViewModel(private val emailService: EmailService = EmailService()) : ViewModel() {

    private val _uiState = MutableStateFlow(YourPhotosScreenState())
    val uiState = _uiState.asStateFlow()

    fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(showSuccessDialog = false) }
    }

    fun submitRequest(
        name: String,
        whatsappNumber: String,
        eventDate: String,
        eventType: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            try {
                emailService.sendYourPhotosRequest(
                    name = name,
                    whatsappNumber = whatsappNumber,
                    eventDate = eventDate,
                    eventType = eventType
                )
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        showSuccessDialog = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        snackbarMessage = "Failed to send request. Please try again later."
                    )
                }
            }
        }
    }
}
