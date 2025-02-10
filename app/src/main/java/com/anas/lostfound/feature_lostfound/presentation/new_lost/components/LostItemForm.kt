package com.anas.lostfound.feature_lostfound.presentation.new_lost.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.anas.lostfound.core.presentation.components.CustomInputTextField
import com.anas.lostfound.core.util.CategoryConstants
import com.anas.lostfound.core.presentation.components.FilterDropDown
import com.anas.lostfound.core.util.ButtonStrings
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.InputFormStrings
import com.anas.lostfound.feature_lostfound.presentation.map_view.MapViewModel
import com.anas.lostfound.feature_lostfound.presentation.map_view.components.SearchBar
import com.anas.lostfound.feature_lostfound.presentation.new_lost.LostItemEvent
import com.anas.lostfound.feature_lostfound.presentation.new_lost.LostItemViewModel
import com.google.android.gms.maps.model.LatLng


@Composable
fun LostItemForm(viewModel: LostItemViewModel, mapViewModel: MapViewModel, isOwner: Boolean) {

    val state by viewModel.state.collectAsState()

    var imageUri by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val categories = CategoryConstants.CATEGORIES

    val readOnly = !isOwner

    LaunchedEffect(state.item) {
        imageUri = state.item.imagePath
        title = state.item.title
        description = state.item.description
        selectedCategory = state.item.category
        contact = state.item.contact
        username = state.item.uid
        email = state.item.email
        location = state.item.location
        address = location
        mapViewModel.selectLocation(LatLng(state.item.latitude, state.item.longitude))
    }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri.toString()
        }
        viewModel.onEvent(LostItemEvent.SelectedImage(imageUri))
    }


    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .padding(4.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
           if(imageUri.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth(),
                )
            } else { Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = ContentDescriptions.GALLERY_SELECT,
                tint = Color.Black,
                modifier = Modifier.size(50.dp)
            )
               }

        }
        Spacer(modifier = Modifier.height(8.dp))


        if(!readOnly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text(ButtonStrings.GALLERY_UPLOAD)
                    Icon(
                        modifier = Modifier.padding(3.dp),
                        imageVector = Icons.Default.Image,
                        contentDescription = ContentDescriptions.GALLERY_SELECT,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .shadow(5.dp, shape = RoundedCornerShape(2.dp))
                .background(Color.White)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier,
            ) {
                Text(InputFormStrings.TITLE)
                CustomInputTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        viewModel.onEvent(LostItemEvent.EnteredTitle(it)) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        ),
                    readOnly = readOnly,

                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
            ) {
                Text(InputFormStrings.DESCRIPTION)
                CustomInputTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        viewModel.onEvent(LostItemEvent.EnteredDescription(it)) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        ),
                    readOnly = readOnly,

                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(InputFormStrings.CONTACT)
                CustomInputTextField(
                    value = contact,
                    onValueChange = {
                        contact = it
                        viewModel.onEvent(LostItemEvent.EnteredContact(it)) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        ),
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(InputFormStrings.EMAIL)
                CustomInputTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        viewModel.onEvent(LostItemEvent.EnteredEmail(it)) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        ),
                    readOnly = readOnly,

                    )
            }


            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(InputFormStrings.LOCATION)
                CustomInputTextField(
                    value = address,
                    onValueChange = {},
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        ),
                    readOnly = true,
                    )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(InputFormStrings.CATEGORY)
                FilterDropDown(
                    fontSize = 15.sp,
                    items = categories,
                    readOnly = readOnly,
                    selectedItem = selectedCategory,

                    onItemSelected = {
                        selectedCategory = it
                        viewModel.onEvent(LostItemEvent.SelectedCategory(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(5.dp)
                )
            }

        }
    }
}


