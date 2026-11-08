package com.example.waraclecakelistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waraclecakelistapp.data.repository.CakeRepository
import com.example.waraclecakelistapp.domain.CakeListProcessor
import com.example.waraclecakelistapp.domain.model.Cake
import com.example.waraclecakelistapp.presentation.state.CakeListUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CakeListViewModel(
    private val repository: CakeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<CakeListUiState>(CakeListUiState.Loading)
    val uiState: StateFlow<CakeListUiState> = _uiState.asStateFlow()

    init {
        loadCakes()
    }

    fun loadCakes() {

        // TODO: Prevent concurrent duplicate API requests when
        // Consider tracking the active coroutine Job to ensure
        // only one request runs at a time.

        _uiState.update { current ->
            if (current is CakeListUiState.Content) {
                current.copy(
                    isRefreshing = true,
                    refreshError = null
                )
            } else {
                CakeListUiState.Loading
            }
        }

        viewModelScope.launch {
            try {
                val cakes = repository.getCakes()
                val processedCakes = CakeListProcessor.process(cakes)

                _uiState.update { current ->
                    if (current is CakeListUiState.Content) {
                        current.copy(
                            cakes = processedCakes,
                            selectedCake = current.selectedCake?.takeIf { it in processedCakes },
                            isRefreshing = false,
                            refreshError = null
                        )
                    } else {
                        CakeListUiState.Content(
                            cakes = processedCakes
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { current ->
                    if (current is CakeListUiState.Content) {
                        current.copy(
                            isRefreshing = false,
                            refreshError = "Unable to refresh cakes."
                        )
                    } else {
                        CakeListUiState.Error(
                            message = "Unable to load cakes."
                        )
                    }
                }
            }
        }
    }

    fun onCakeSelected(cake: Cake){
        _uiState.update { current ->
            if (current is CakeListUiState.Content) {
                current.copy(selectedCake = cake)
            } else {
                current
            }
        }
    }

    fun onCakeDismissed(){
        _uiState.update { current ->
            if (current is CakeListUiState.Content) {
                current.copy(selectedCake = null)
            } else {
                current
            }
        }
    }

    fun onRefreshErrorDismissed() {
        _uiState.update { current ->
            if (current is CakeListUiState.Content) {
                current.copy(refreshError = null)
            } else {
                current
            }
        }
    }
}