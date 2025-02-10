package com.anas.lostfound.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropDown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderStroke: Dp = 1.dp,
    fontSize: TextUnit,
    readOnly : Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = if(readOnly){
                isExpanded
            } else !isExpanded
        }
    ) {
        // TextField-like clickable area
        Text(
            text = selectedItem,

            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
                .background(color = MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(borderStroke, Color.Transparent),
                    RoundedCornerShape(10.dp),
                )
                .padding(11.dp),
            fontSize = fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }

        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, fontSize = fontSize) },
                    onClick = {
                        onItemSelected(item)
                        isExpanded = false
                    }

                )
            }
        }
    }
}

