package com.anas.lostfound.core.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anas.lostfound.core.util.ContentDescriptions


@Composable
fun DeleteButton(
    onDeleteClick: () -> Unit, // returns a unit
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            onDeleteClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = ContentDescriptions.DELETE,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )
    }
}


