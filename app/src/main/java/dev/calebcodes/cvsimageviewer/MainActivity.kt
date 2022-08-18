package dev.calebcodes.cvsimageviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.calebcodes.cvsimageviewer.navigation.Screen
import dev.calebcodes.cvsimageviewer.ui.ImageListScreen
import dev.calebcodes.cvsimageviewer.ui.detailScreen.ImageDetailScreen
import dev.calebcodes.cvsimageviewer.ui.theme.CvsImageViewerTheme
import dev.calebcodes.cvsimageviewer.viewmodel.ImageListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CvsImageViewerTheme {
                val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ImageListScreen.route
                ) {
                    composable(
                        route = Screen.ImageListScreen.route
                    ) {
                        val model = viewModel<ImageListViewModel>(viewModelStoreOwner = viewModelStoreOwner)
                        ImageListScreen(model, navController)
                    }
                    composable(
                        route = Screen.ImageDetailScreen.route + "/{imageId}",
                        arguments = listOf(navArgument("imageId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                            val model = viewModel<ImageListViewModel>()
                            ImageDetailScreen(model, backStackEntry.arguments?.getString("imageId"))
                        }
                    }
                }
            }
        }
    }
}
