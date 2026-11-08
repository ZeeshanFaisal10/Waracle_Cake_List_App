package com.example.waraclecakelistapp.presentation.viewmodel

import com.example.waraclecakelistapp.data.repository.FakeCakeRepository
import com.example.waraclecakelistapp.domain.model.Cake
import com.example.waraclecakelistapp.presentation.state.CakeListUiState
import com.example.waraclecakelistapp.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CakeListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial load removes duplicates and sorts cakes`() = runTest {

        val repository = FakeCakeRepository {
            listOf(
                Cake("Zebra Cake", "Description 1", "image1"),
                Cake("banana Cake", "Description 2", "image2"),
                Cake("Apple Cake", "Description 3", "image3"),
                Cake("Banana Cake", "Duplicate", "image4")
            )
        }

        val viewModel = CakeListViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CakeListUiState.Content)

        val content = state as CakeListUiState.Content

        assertEquals(
            listOf(
                "Apple Cake",
                "banana Cake",
                "Zebra Cake"
            ),
            content.cakes.map { it.title }
        )

        assertEquals(
            "Description 2",
            content.cakes[1].description
        )
    }

    @Test
    fun `initial load failure present error state`() = runTest {

        val repository = FakeCakeRepository {
            throw IOException("No internet connection")
        }

        val viewModel = CakeListViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CakeListUiState.Error)

        val errorState = state as CakeListUiState.Error

        assertEquals(
            "Unable to load cakes.",
            errorState.message
        )
    }
}