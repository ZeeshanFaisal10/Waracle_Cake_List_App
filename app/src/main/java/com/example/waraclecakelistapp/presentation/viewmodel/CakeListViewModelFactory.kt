package com.example.waraclecakelistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waraclecakelistapp.data.repository.CakeRepository

class CakeListViewModelFactory(
    private val repository: CakeRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CakeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CakeListViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}