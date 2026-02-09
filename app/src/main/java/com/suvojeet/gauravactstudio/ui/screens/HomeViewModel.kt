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
    val banners: List<BannerData> = emptyList(),
    val popularServices: List<Service> = emptyList(),
    val featuredWorks: List<CloudinaryResource> = emptyList(),
    val isLoading: Boolean = true
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Load static data
            val banners = StudioRepository.banners
            val services = StudioRepository.services.filter { it.isHighlighted }

            // Load dynamic data (Featured Works)
            val featuredResult = StudioRepository.getFeaturedPhotos()
            val featuredWorks = featuredResult.getOrNull() ?: emptyList()

            _uiState.update { 
                it.copy(
                    banners = banners,
                    popularServices = services,
                    featuredWorks = featuredWorks,
                    isLoading = false
                )
            }
        }
    }
}
