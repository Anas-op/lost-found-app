package com.anas.lostfound.feature_lostfound.presentation.lost_found_items

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.TabStrings
import com.anas.lostfound.feature_lostfound.presentation.util.TabItem
import com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.found.FoundSection
import com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.lost.LostSection
import com.anas.lostfound.feature_lostfound.presentation.register_login.AuthState
import com.anas.lostfound.feature_lostfound.presentation.register_login.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LostFoundScreen(
    navController: NavController, authViewModel: AuthViewModel
) {

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }


    val tabItems = listOf(
        TabItem(
            title = TabStrings.LOST,
            unselectedIcon = Icons.Outlined.Search,
            selectedIcon = Icons.Filled.Search
        ), TabItem(
            title = TabStrings.FOUND,
            unselectedIcon = Icons.Outlined.LocationSearching,
            selectedIcon = Icons.Filled.LocationSearching
        )
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // side bar state
    val scope = rememberCoroutineScope()


    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }


    ModalNavigationDrawer(
        modifier = Modifier.fillMaxHeight(),
        drawerState = drawerState,
        drawerContent = {


            ModalDrawerSheet(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight(),
                (RoundedCornerShape(0.dp)),
                windowInsets = WindowInsets(top = 0),
                drawerContainerColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Row() {

                    Column(
                        Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .padding(top = 80.dp, end = 16.dp, start = 16.dp, bottom = 16.dp)
                    ) {


                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Icon",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(Modifier.padding(10.dp))

                        Text(
                            text = FirebaseAuth.getInstance().currentUser?.email ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal

                        )
                    }


                }

                HorizontalDivider()


                Row(modifier = Modifier, horizontalArrangement = Arrangement.Start) {

                    Button(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        shape = RoundedCornerShape(0),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        border = BorderStroke(0.dp, Color.Transparent),
                        contentPadding = PaddingValues(0.dp),

                        onClick = {
                            authViewModel.signout()
                        },

                        ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(10.dp),
                                tint = Color.Black,
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "ContentDescriptions.Exit"
                            )

                            Text(
                                "Logout",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )

                        }


                    }
                }

            }


        }) {
        Scaffold(

            topBar = {

                CenterAlignedTopAppBar(

                    title = {
                        Text(
                            text = if (selectedTabIndex == 0) TabStrings.LOST else TabStrings.FOUND,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.scrim,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        scrolledContainerColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.scrim,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() } // open sorting menu
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = ContentDescriptions.SORTING_MENU
                            )
                        }
                    },
                    actions = {},
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )
            },
            ) { paddingValues ->
            val paddingTop = paddingValues.calculateTopPadding()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingTop)
            ) {

                // with horizontal page we define the content inside the tap
                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                        index ->
                    when (index) {
                        0 -> {
                            // Content for the "Lost" tab
                            LostSection(navController)
                        }

                        1 -> {
                            // Content for the "Found" tab
                            FoundSection(navController)
                        }
                    }
                }

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                            shape = RectangleShape
                        )
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        ),
                ) {
                    tabItems.forEachIndexed { index, item ->
                        Tab(selected = index == selectedTabIndex, onClick = {
                            selectedTabIndex = index
                        }, text = {
                            Text(text = item.title)
                        }, icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon, contentDescription = item.title
                            )
                        })
                    }
                }
            }
        }

    }


}


