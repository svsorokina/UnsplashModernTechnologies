package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.sorokina.unsplash.modern.interactor.common.Result
import ru.sorokina.unsplash.modern.ui.photos.data.PhotosUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = PhotosViewModel(),
) {

    val uiState: PhotosUi by viewModel.uiState.collectAsState()

    when (val photos = uiState.photos) {
        is Result.Loading -> Text("Loading")
        is Result.Success -> {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp,
            ) {
                itemsIndexed(photos.data) { index, item ->

                    val modifier = when (index) {
                        0 -> Modifier.clip(RoundedCornerShape(topStart = 16.dp))
                        1 -> Modifier.clip(RoundedCornerShape(topEnd = 16.dp))
                        else -> Modifier
                    }

                    val url = item.urls.regular
                    ImageLoader(url, modifier)
                }
            }
        }
        is Result.Error -> Text("Error")
    }
}

@Composable
fun ImageLoader(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth(),
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Fit
    )
}