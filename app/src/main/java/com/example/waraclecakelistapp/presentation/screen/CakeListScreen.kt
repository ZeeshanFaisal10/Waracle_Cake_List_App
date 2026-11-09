
package com.example.waraclecakelistapp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.waraclecakelistapp.domain.model.Cake
import com.example.waraclecakelistapp.presentation.components.CakeItem
import com.example.waraclecakelistapp.presentation.components.CakeDescriptionDialog
import com.example.waraclecakelistapp.presentation.state.CakeListUiState

@Composable
fun CakeListScreen(
    uiState: CakeListUiState,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onCakeSelected: (Cake) -> Unit,
    onCakeDismissed: () -> Unit,
    onRefreshErrorDismissed: () -> Unit,
) {
    when (uiState) {

        CakeListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CakeListUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }
        }

        is CakeListUiState.Content -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cakes",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    TextButton(
                        onClick = onRefresh,
                        enabled = !uiState.isRefreshing
                    ) {
                        Text(
                            if (uiState.isRefreshing) {
                                "Refreshing..."
                            } else {
                                "Refresh"
                            }
                        )
                    }
                }

                if (uiState.refreshError != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.refreshError,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.error
                        )

                        TextButton(
                            onClick = onRefreshErrorDismissed
                        ) {
                            Text("Dismiss")
                        }
                    }
                }

                if (uiState.cakes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No cakes available",
                            textAlign = TextAlign.Center
                        )
                    }
                } else {

                    val listState = rememberLazyListState()

                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f)
                    ) {
                        itemsIndexed(
                            items = uiState.cakes,
                            key = { _, cake ->
                                cake.title.trim().lowercase()
                            }
                        ) { index, cake ->

                            var visible by remember(cake.title) {
                                mutableStateOf(false)
                            }

                            LaunchedEffect(cake.title) {
                                visible = true
                            }

                            val alpha by animateFloatAsState(
                                targetValue = if (visible) 1f else 0f,
                                animationSpec = tween(400),
                                label = "Cake fade"
                            )

                            val offsetY by animateDpAsState(
                                targetValue = if (visible) 0.dp else (-16).dp,
                                animationSpec = tween(400),
                                label = "Cake slide"
                            )

                            Column(
                                modifier = Modifier.graphicsLayer {
                                    this.alpha = alpha
                                    translationY = offsetY.toPx()
                                }
                            ) {
                                CakeItem(
                                    cake = cake,
                                    onClick = {
                                        onCakeSelected(cake)
                                    }
                                )

                                if (index < uiState.cakes.lastIndex) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }

            uiState.selectedCake?.let { cake ->
                CakeDescriptionDialog(
                    cake = cake,
                    onDismiss = onCakeDismissed
                )
            }
        }
    }
}
