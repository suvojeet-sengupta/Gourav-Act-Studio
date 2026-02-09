package com.suvojeet.gauravactstudio.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suvojeet.gauravactstudio.data.model.Service
import com.suvojeet.gauravactstudio.data.repository.StudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ServicesUiState(
    val services: List<Service> = emptyList(),
    val filteredServices: List<Service> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val searchQuery: String = ""
)

class ServicesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServicesUiState())
    val uiState = _uiState.asStateFlow()

    private val allServices = StudioRepository.services

    init {
        // Initialize with all services
        val categories = listOf("All") + allServices.map { it.category }.distinct()
        _uiState.update { 
            it.copy(
                services = allServices, 
                filteredServices = allServices,
                categories = categories
            ) 
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        filterServices()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterServices()
    }

    private fun filterServices() {
        val currentState = _uiState.value
        val query = currentState.searchQuery
        val category = currentState.selectedCategory

        val filtered = allServices.filter { service ->
            (category == "All" || service.category == category) &&
            (service.title.contains(query, ignoreCase = true) || 
             service.description.contains(query, ignoreCase = true))
        }

        _uiState.update { it.copy(filteredServices = filtered) }
    }
}
