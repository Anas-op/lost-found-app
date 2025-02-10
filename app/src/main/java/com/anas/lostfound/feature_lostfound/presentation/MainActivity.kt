package com.anas.lostfound.feature_lostfound.presentation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anas.lostfound.feature_lostfound.presentation.lost_found_items.LostFoundScreen
import com.anas.lostfound.feature_lostfound.presentation.new_found.FoundItemScreen
import com.anas.lostfound.feature_lostfound.presentation.util.Screen
import com.anas.lostfound.feature_lostfound.presentation.new_lost.LostItemScreen
import com.anas.lostfound.feature_lostfound.presentation.register_login.AuthViewModel
import com.anas.lostfound.feature_lostfound.presentation.register_login.LoginScreen
import com.anas.lostfound.feature_lostfound.presentation.register_login.SignupScreen
import com.example.compose.LostFoundTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.libraries.places.api.Places

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCFEWQtxW_yG1KyRqnZ85NFGDTQpRq-KXo")
            enableEdgeToEdge()
            setContent {
                LostFoundTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()
                        val authViewModel: AuthViewModel by viewModels()

                        NavHost(
                            navController = navController,
                            startDestination = Screen.LoginScreen.route
                        ) {

                            composable(Screen.LoginScreen.route) {
                                LoginScreen(navController, authViewModel)
                            }
                            composable(Screen.SignupScreen.route) {
                                SignupScreen(navController, authViewModel)
                            }


                            composable(route = Screen.LostFoundScreen.route) {
                                LostFoundScreen(
                                    navController = navController,
                                    authViewModel
                                )
                            }


                            composable(
                                route = Screen.NewLostItemScreen.route + "?itemId={itemId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "itemId"
                                    )
                                    {
                                        type = NavType.IntType
                                        defaultValue =
                                            -1
                                    // if I click on new it's gonna give me a default id -1
                                    // in the input form
                                    }
                                )
                            )
                            {
                                LostItemScreen(navController = navController)
                            }

                            composable(
                                route = Screen.NewFoundItemScreen.route + "?itemId={itemId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "itemId"
                                    )
                                    {
                                        type = NavType.IntType
                                        defaultValue =
                                            -1
                                    // if I click on new it's gonna give me a default
                                    // id -1 in the input form
                                    }
                                )
                            )
                            {
                                FoundItemScreen(navController = navController)
                            }

                        }

                    }
                }
            }
        }
    }

    // clear cache on exit
    override fun onDestroy() {
        super.onDestroy()
        try {
            val cacheDir = cacheDir
            cacheDir.deleteRecursively() // Deletes all files and subdirectories in the cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

