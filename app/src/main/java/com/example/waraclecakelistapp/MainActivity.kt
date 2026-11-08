package com.example.waraclecakelistapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.waraclecakelistapp.ui.theme.WaracleCakeListAppTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository =
            (application as CakeApplication).appContainer.cakeRepository
        //Testing whether Cakes API implemented correctly
        lifecycleScope.launch {
            try {
                val cakes = repository.getCakes()

                Log.d("CakeApi", "Successfully fetched ${cakes.size} cakes")

                cakes.forEach { cake ->
                    Log.d(
                        "CakeApi",
                        "Title: ${cake.title}, Description: ${cake.description}, Image: ${cake.imageUrl}"
                    )
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("CakeApi", "Failed to fetch cakes", e)
            }
        }
        setContent {
            WaracleCakeListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WaracleCakeListAppTheme {
        Greeting("Android")
    }
}