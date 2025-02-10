package com.anas.lostfound.feature_lostfound.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.InputFormStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    searchHistory: List<String>,
    onHistoryItemClick: (String) -> Unit
) {
    DockedSearchBar(
        shadowElevation = 5.dp,
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surfaceContainer),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(it)
            onActiveChange(false)
        },
        active = active,
        shape = RoundedCornerShape(10.dp),
        onActiveChange = onActiveChange,
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp).fillMaxWidth(),
        enabled = true,
        placeholder = { Text(InputFormStrings.SEARCH_ITEM) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = ContentDescriptions.SEARCH
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            onQueryChange("")
                        } else {
                            onActiveChange(false)
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = ContentDescriptions.CLOSE_SEARCH,
                )
            }
        }
    ) {
        searchHistory.takeLast(3).forEach { item ->
            ListItem(
                modifier = Modifier.clickable { onHistoryItemClick(item) },
                headlineContent = { Text(text = item) },
                leadingContent = {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = ContentDescriptions.HISTORY_ICON,
                    )
                }
            )
        }
    }
}
