package com.example.waraclecakelistapp.presentation.state

import com.example.waraclecakelistapp.domain.model.Cake

sealed interface CakeListUiState {
    data object Loading: CakeListUiState

    data class  Content(
        val cakes: List<Cake>,
        val selectedCake: Cake? = null,
        val isRefreshing: Boolean = false,
        val refreshError: String? = null
    ): CakeListUiState

    data class Error(val message: String): CakeListUiState
}
