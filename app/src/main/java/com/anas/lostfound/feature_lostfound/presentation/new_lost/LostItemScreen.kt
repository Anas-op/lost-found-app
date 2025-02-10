package com.anas.lostfound.feature_lostfound.presentation.new_lost

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anas.lostfound.core.presentation.components.DeleteButton
import com.anas.lostfound.core.util.ButtonStrings
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.NewUpdateStrings
import com.anas.lostfound.feature_lostfound.presentation.map_view.MapViewModel
import com.anas.lostfound.feature_lostfound.presentation.map_view.components.MapBox
import com.anas.lostfound.feature_lostfound.presentation.map_view.components.SearchBar
import com.anas.lostfound.feature_lostfound.presentation.new_lost.components.LostItemForm
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
) // we will define padding by ourselves
@ExperimentalMaterial3Api
@Composable
fun LostItemScreen(
    navController: NavController,
    viewModel: LostItemViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    // Track screen orientation
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val topBarHeight = if (isPortrait) 64.dp else 0.dp

    val scrollState = rememberScrollState()
    val isOwner = FirebaseAuth.getInstance().currentUser?.uid.toString() == state.item.uid


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                LostItemViewModel.UiEvent.Back -> navController.navigateUp()
                LostItemViewModel.UiEvent.SaveItem -> navController.navigateUp()
                is LostItemViewModel.UiEvent.showSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.onPrimary,

        topBar = {},
        bottomBar = {
            if (isOwner) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f)) // Optional: Dim the screen
                            .clickable(enabled = false) {} // Blocks interactions
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else
                    BottomAppBar(
                        actions = {
                            DeleteButton(
                                onDeleteClick = {
                                    scope.launch {
                                        val confirm = snackbarHostState.showSnackbar(
                                            message = NewUpdateStrings.CONFIRM_DELETE,
                                            actionLabel = NewUpdateStrings.YES
                                        )
                                        if (confirm == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(LostItemEvent.Delete)
                                        }
                                    }
                                }
                            )
                            // You could add more actions here if needed
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    viewModel.onEvent(LostItemEvent.SaveItem)
                                },
                                shape = CircleShape,
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = ContentDescriptions.SAVE_ITEM,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                    )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = topBarHeight)

                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .fillMaxHeight()

            ) {


                Row {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),

                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        LostItemForm(viewModel, mapViewModel, isOwner)
                    }
                }


                Row(modifier = Modifier
                    .height(400.dp)
                    .padding(16.dp)) {

                    Column {
                        if(isOwner) {
                            SearchBar(
                                modifier = Modifier,
                                onPlaceSelected = { latLng ->
                                    mapViewModel.selectLocation(latLng)
                                    mapViewModel.getAddressFromCoordinates(
                                        ctx,
                                        latLng.latitude,
                                        latLng.longitude
                                    ) { result ->
                                        val address = result ?: ""
                                        viewModel.onEvent(
                                            LostItemEvent.UpdateCoordinates(
                                                latLng.latitude,
                                                latLng.longitude,
                                                address
                                            )
                                        )
                                    }
                                },
                            )
                        }

                        MapBox(
                            modifier = Modifier.fillMaxSize(),
                            userLocation = mapViewModel.userLocation.value,
                            selectedLocation = mapViewModel.selectedLocation.value,
                            onMapClick = {}
                        )

                    }


                }

                if (state.item.contact.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                val u = Uri.parse("tel:" + state.item.contact)
                                val i = Intent(Intent.ACTION_DIAL, u)
                                try {
                                    ctx.startActivity(i)
                                } catch (s: SecurityException) {

                                    Toast.makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                        .show()
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(100.dp),
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Text(ButtonStrings.CALL)
                            Icon(
                                modifier = Modifier.padding(3.dp),
                                imageVector = Icons.Default.Call,
                                contentDescription = ContentDescriptions.CALL,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))

            }
        }
    }
}


