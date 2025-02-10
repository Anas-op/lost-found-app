package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.lost


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anas.lostfound.core.util.CategoryConstants
import com.anas.lostfound.feature_lostfound.presentation.components.SearchBar
import com.anas.lostfound.core.presentation.components.FilterDropDown
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.feature_lostfound.presentation.lost_found_items.components.LostItemCard
import com.anas.lostfound.feature_lostfound.presentation.util.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UseOfNonLambdaOffsetOverload")
@Composable
fun LostSection(
    navController: NavController, viewModel: LostItemListViewModel = hiltViewModel()
) {

    val categories = CategoryConstants.CATEGORIES
    var selectedCategory by remember { mutableStateOf(categories[0]) } // Selected category
    val state = viewModel.state.value


    // for hosting snackbars, if I delete an item I get a snackbar to undo the item
    val snackbarHostState = remember { SnackbarHostState() }

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val itemsState = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = true) {
        viewModel.getLostItems()
    }

    val filteredItems = state.lostItems.filter { item ->
        item.title.contains(
            text,
            ignoreCase = true
        ) && (selectedCategory == categories.first() || item.category.contains(selectedCategory))
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.NewLostItemScreen.route)
            }, shape = CircleShape, containerColor = MaterialTheme.colorScheme.primary


        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ContentDescriptions.ADD_ITEM,
                tint = MaterialTheme.colorScheme.onPrimary
            )

        }

    },

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }, topBar = ({
            SearchBar(query = text,
                onQueryChange = { text = it },
                onSearch = {
                    itemsState.add(text)
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                searchHistory = itemsState,
                onHistoryItemClick = { text = it })
        })

    ) {

        Box(

            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary)

        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        text = "Category:",
                        modifier = Modifier.padding(5.dp),
                        fontSize = 12.sp,
                        lineHeight = 10.sp
                    )
                    FilterDropDown(
                        items = categories,
                        selectedItem = selectedCategory,
                        onItemSelected = { category ->
                            selectedCategory = category
                        },
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 12.dp)
                            .width(100.dp),
                        fontSize = 12.sp
                    )
                }


                // here we have items
                LazyVerticalGrid(
                    modifier = Modifier,
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(bottom = 30.dp, top = 10.dp, start = 5.dp),
                ) {


                    // here we populate the app
                    items(filteredItems.filter { it.lost }) { item ->
                        LostItemCard(
                            lostItem = item,
                            onCardClick = {

                                navController.navigate(
                                    Screen.NewLostItemScreen.route + "?itemId=${item.id}"
                                )
                            },
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }


                }
            }

            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(Modifier.semantics {
                        this.contentDescription = ContentDescriptions.LOADING_INDICATOR
                    })
                }
            }
            if (state.error != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error, fontSize = 30.sp, lineHeight = 36.sp
                    )
                }
            }
        }
    }


}


