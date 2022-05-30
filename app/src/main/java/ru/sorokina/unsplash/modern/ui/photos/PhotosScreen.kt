package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ru.sorokina.unsplash.modern.interactor.common.Result
import ru.sorokina.unsplash.modern.ui.common.LazyStaggeredGrid
import ru.sorokina.unsplash.modern.ui.photos.data.PhotosUi

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = PhotosViewModel(),
) {

    val uiState: PhotosUi by viewModel.uiState.collectAsState()

    val photos = uiState.photos

    when (photos) {
        is Result.Loading -> Text("Loading")
        is Result.Success -> {
            LazyStaggeredGrid(2) {
                photos.data.forEach {
                    item {
                        val url = it.urls.regular
                        ImageLoader(url)
                    }
                }
            }
        }
        is Result.Error -> Text("Error")
    }
}

@Composable
fun ImageLoader(url: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Fit
    )
}