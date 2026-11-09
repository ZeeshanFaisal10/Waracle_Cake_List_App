package com.example.waraclecakelistapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.waraclecakelistapp.domain.model.Cake

@Composable
fun CakeItem(
    cake: Cake,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(
                role = Role.Button,
                onClick = onClick
            ).padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // TODO: Will provide a bundled drawable fallback
        // so unavailable images can be represented without network access.
        val fallbackUrl =
            "https://www.pngkey.com/png/detail/233-2332677_image-500580-placeholder-transparent.png"

        var useFallback by remember(cake.imageUrl) {
            mutableStateOf(false)
        }

        AsyncImage(
            model = if (useFallback || cake.imageUrl.isBlank()) {
                fallbackUrl
            } else {
                cake.imageUrl
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onError = {
                if (!useFallback) {
                    useFallback = true
                }
            },
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Text(
            text = cake.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}
