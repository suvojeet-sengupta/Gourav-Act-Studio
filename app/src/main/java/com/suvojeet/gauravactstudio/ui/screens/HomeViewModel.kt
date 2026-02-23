package com.suvojeet.gauravactstudio.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvojeet.gauravactstudio.data.model.CloudinaryResource
import com.suvojeet.gauravactstudio.data.model.Service
import com.suvojeet.gauravactstudio.data.repository.BannerData
import com.suvojeet.gauravactstudio.data.repository.StudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val banners: List<BannerData> = StudioRepository.banners,
    val popularServices: List<Service> = StudioRepository.services.filter { it.isHighlighted },
    val featuredWorks: List<CloudinaryResource> = emptyList(),
    val isLoadingFeatured: Boolean = true,
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDynamicData()
    }

    fun retry() {
        loadDynamicData()
    }

    private fun loadDynamicData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingFeatured = true, errorMessage = null) }

            try {
                // Load dynamic data (Featured Works)
                val featuredResult = StudioRepository.getFeaturedPhotos()
                
                if (featuredResult.isSuccess) {
                    val featuredWorks = featuredResult.getOrNull() ?: emptyList()
                    _uiState.update { 
                        it.copy(
                            featuredWorks = featuredWorks,
                            isLoadingFeatured = false
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            errorMessage = "Gallery partially unavailable.",
                            isLoadingFeatured = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        errorMessage = "Error loading latest works.",
                        isLoadingFeatured = false
                    )
                }
            }
        }
    }
}
