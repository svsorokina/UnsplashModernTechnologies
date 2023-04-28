package ru.sorokina.unsplash.modern.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import ru.sorokina.unsplash.modern.ui.photos.PhotoDetailScreen
import ru.sorokina.unsplash.modern.ui.photos.PhotosScreen
import ru.sorokina.unsplash.modern.ui.photos.PhotosViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // https://issuetracker.google.com/issues/177245496
    // https://stackoverflow.com/questions/66744977/save-and-retain-lazycolumn-scroll-position-while-using-paging-3
    val viewModel: PhotosViewModel = viewModel(factory = PhotosViewModel.Factory)
    val photos = viewModel.getPhotos().collectAsLazyPagingItems()

    NavHost(
        navController = navController,
        startDestination = PhotosList.route,
        modifier = modifier
    ) {
        composable(route = PhotosList.route) {
            PhotosScreen(
                photos = photos,
                onPhotoClick = { photoId ->
                    navController.navigateSingleTopTo("${PhotoDetail.route}/$photoId")
                }
            )
        }
        composable(
            route = PhotoDetail.routeWithArgs,
            arguments = PhotoDetail.arguments
        ) { navBackStackEntry ->
            val photoId = navBackStackEntry.arguments?.getSerializable(PhotoDetail.photoIdArg)
            val photo = photos.itemSnapshotList.items.find { it.id == photoId }
            photo?.let {
                PhotoDetailScreen(
                    photo = photo,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }