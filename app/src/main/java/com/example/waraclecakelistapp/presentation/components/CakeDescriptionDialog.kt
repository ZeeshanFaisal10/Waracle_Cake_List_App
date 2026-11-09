package com.example.waraclecakelistapp.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.waraclecakelistapp.domain.model.Cake

@Composable
fun CakeDescriptionDialog(
    cake: Cake,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = cake.title)
        },
        text = {
            Text(text = cake.description)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Close")
            }
        }
    )
}