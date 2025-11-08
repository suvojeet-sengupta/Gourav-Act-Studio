import java.util.UUID // Import UUID
import com.suvojeet.gauravactstudio.R // Import R for string resources
import androidx.compose.ui.res.stringResource // Import stringResource

data class PricingScreenState(
    val showBookingDialog: Boolean = false,
    val selectedPackage: String = "",
    val customPackageDetails: String? = null,
    val isSubmittingInquiry: Boolean = false,
    val snackbarMessage: String? = null,
    val bookingRequestNumber: String? = null, // New: Booking request number
    val showBookingConfirmation: Boolean = false // New: Flag to show confirmation
)

class PricingViewModel(private val emailService: EmailService = EmailService()) : ViewModel() {

    private val _uiState = MutableStateFlow(PricingScreenState())
    val uiState = _uiState.asStateFlow()

    fun onChoosePlan(packageName: String, customPackageDetails: String? = null) {
        _uiState.update { it.copy(showBookingDialog = true, selectedPackage = packageName, customPackageDetails = customPackageDetails) }
    }

    fun onDismissBookingDialog() { // Renamed function
        if (!_uiState.value.isSubmittingInquiry) {
            _uiState.update { it.copy(showBookingDialog = false, customPackageDetails = null, bookingRequestNumber = null, showBookingConfirmation = false) } // Reset new fields
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
            val bookingReqNum = UUID.randomUUID().toString().substring(0, 8).uppercase() // Generate booking number
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
                    location = location,
                    bookingRequestNumber = bookingReqNum // Pass booking number
                )
                _uiState.update {
                    it.copy(
                        showBookingDialog = false, // Close dialog
                        snackbarMessage = "Booking request sent successfully! We will contact you soon.", // Use string resource later
                        customPackageDetails = null,
                        bookingRequestNumber = bookingReqNum, // Set booking number
                        showBookingConfirmation = true, // Show confirmation
                        isSubmittingInquiry = false // Reset submitting flag
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarMessage = "Failed to send booking request. Please try again later.", // Use string resource later
                        isSubmittingInquiry = false // Reset submitting flag
                    )
                }
            }
        }
    }
}