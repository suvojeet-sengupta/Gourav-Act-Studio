package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.data.repository.StudioRepository
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PhotosUiState(
    val categories: List<PortfolioItem> = emptyList(),
    val albums: List<Album> = emptyList(),
    val isLoadingAlbums: Boolean = true,
    val errorMessage: String? = null
)

class PhotosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PhotosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun retry() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAlbums = true, errorMessage = null) }
            
            try {
                // Load categories from repository
                val categories = StudioRepository.portfolioCategories
                _uiState.update { it.copy(categories = categories) }

                // Load albums from repository
                val result = StudioRepository.getAlbums()
                
                if (result.isSuccess) {
                    _uiState.update { 
                        it.copy(
                            albums = result.getOrNull() ?: emptyList(),
                            isLoadingAlbums = false
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            errorMessage = "Failed to load albums. Please check your connection.",
                            isLoadingAlbums = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        errorMessage = "An unexpected error occurred.",
                        isLoadingAlbums = false
                    )
                }
            }
        }
    }
}