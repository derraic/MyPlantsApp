package com.derra.myplantsapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.derra.myplantsapp.add_edit_plant.AddEditPlantScreen
import com.derra.myplantsapp.detail_plant.DetailPlantScreen
import com.derra.myplantsapp.plants_list.PlantListScreen
import com.derra.myplantsapp.ui.theme.MyPlantsAppTheme
import com.derra.myplantsapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //setFullscreen()
        setContent {
            MyPlantsAppTheme {
                //PlantListScreen()


                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Routes.PLANT_LIST) {
                    composable(Routes.PLANT_LIST) {
                        PlantListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(Routes.DETAIL_PLANT + "?plantId={plantId}", 
                        arguments = listOf(
                            navArgument(name = "plantId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                            
                        )
                    ) {
                        DetailPlantScreen(onPopBackStack = { navController.popBackStack() }, onNavigate = {
                            navController.navigate(it.route)
                        })
                    }


                    composable(
                        route = Routes.ADD_EDIT_PLANT + "?plantId={plantId}",
                        arguments = listOf(
                            navArgument(name = "plantId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val activity = LocalContext.current as Activity
                        AddEditPlantScreen(onPopBackStack = { navController.popBackStack()}, activity = activity, context = applicationContext)
                    }


                }
            }
        }
    }

    private fun setFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }




}

