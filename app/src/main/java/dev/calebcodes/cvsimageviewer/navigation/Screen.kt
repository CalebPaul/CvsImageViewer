package dev.calebcodes.cvsimageviewer.navigation

sealed class Screen(val route: String) {
    object ImageListScreen : Screen("image_list_screen")
    object ImageDetailScreen : Screen("image_detail_screen")
}
