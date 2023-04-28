package ru.sorokina.unsplash.modern.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object PhotosList : Destination {
    override val route = "photos_list"
}

object PhotoDetail : Destination {
    override val route = "photo_detail"

    const val photoIdArg = "photo_id"
    val routeWithArgs = "${route}/{${photoIdArg}}"
    val arguments = listOf(
        navArgument(photoIdArg) { type = NavType.StringType }
    )
}