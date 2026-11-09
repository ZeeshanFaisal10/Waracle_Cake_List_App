package com.example.waraclecakelistapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.waraclecakelistapp.presentation.screen.CakeListScreen
import com.example.waraclecakelistapp.presentation.viewmodel.CakeListViewModel
import com.example.waraclecakelistapp.presentation.viewmodel.CakeListViewModelFactory
import com.example.waraclecakelistapp.ui.theme.WaracleCakeListAppTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : ComponentActivity() {

    private val cakeListViewModel: CakeListViewModel by viewModels {
        CakeListViewModelFactory(
            (application as CakeApplication)
                .appContainer.cakeRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val uiState by cakeListViewModel.uiState.collectAsStateWithLifecycle()

                CakeListScreen(
                    uiState = uiState,
                    onRefresh = cakeListViewModel::loadCakes,
                    onRetry = cakeListViewModel::loadCakes,
                    onCakeSelected = cakeListViewModel::onCakeSelected,
                    onCakeDismissed = cakeListViewModel::onCakeDismissed,
                    onRefreshErrorDismissed = cakeListViewModel::onRefreshErrorDismissed
                )
            }
        }
    }
}