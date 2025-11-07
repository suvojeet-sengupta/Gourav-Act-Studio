package com.suvojeet.gauravactstudio.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvojeet.gauravactstudio.util.EmailService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PricingScreenState(
    val showInquiryDialog: Boolean = false,
    val selectedPackage: String = "",
    val customPackageDetails: String? = null,
    val isSubmittingInquiry: Boolean = false,
    val snackbarMessage: String? = null
)

class PricingViewModel(private val emailService: EmailService = EmailService()) : ViewModel() {

    private val _uiState = MutableStateFlow(PricingScreenState())
    val uiState = _uiState.asStateFlow()

    fun onChoosePlan(packageName: String, customPackageDetails: String? = null) {
        _uiState.update { it.copy(showInquiryDialog = true, selectedPackage = packageName, customPackageDetails = customPackageDetails) }
    }

    fun onDismissInquiryDialog() {
        if (!_uiState.value.isSubmittingInquiry) {
            _uiState.update { it.copy(showInquiryDialog = false, customPackageDetails = null) }
        }
    }

    fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun onSubmitInquiry(
        name: String,
        phone: String,
        eventType: String,
        otherEventType: String,
        date: String,
        notes: String,
        location: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmittingInquiry = true) }
            try {
                emailService.sendEmail(
                    name = name,
                    phone = phone,
                    eventType = eventType,
                    otherEventType = otherEventType,
                    date = date,
                    notes = notes,
                    packageName = _uiState.value.selectedPackage,
                    customPackageDetails = _uiState.value.customPackageDetails,
                    location = location
                )
                _uiState.update {
                    it.copy(
                        showInquiryDialog = false,
                        snackbarMessage = "Inquiry sent successfully! We will contact you soon.",
                        customPackageDetails = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(snackbarMessage = e.message ?: "An unknown error occurred.")
                }
            } finally {
                _uiState.update { it.copy(isSubmittingInquiry = false) }
            }
        }
    }
}