package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import ru.sorokina.unsplash.modern.domain.Photo

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = PhotosViewModel(),
) {
    val lazyPhotoItems: LazyPagingItems<Photo> = viewModel.getPhotos().collectAsLazyPagingItems()
    PhotosList(lazyPhotoItems)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosList(
    lazyPhotoItems: LazyPagingItems<Photo>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (lazyPhotoItems.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is LoadState.Error -> {
                val e = lazyPhotoItems.loadState.refresh as LoadState.Error
                ErrorItem(
                    message = e.error.localizedMessage!!,
                    onClickRetry = {
                        lazyPhotoItems.retry()
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp,
                ) {
                    items(
                        lazyPhotoItems.itemCount
                    ) { index ->
                        val modifier = when (index) {
                            0 -> Modifier.clip(RoundedCornerShape(topStart = 16.dp))
                            1 -> Modifier.clip(RoundedCornerShape(topEnd = 16.dp))
                            else -> Modifier
                        }

                        val url = lazyPhotoItems[index]?.urls?.regular.orEmpty()
                        ImageLoader(url, modifier)
                    }

                    lazyPhotoItems.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {
                                item(
                                    span = StaggeredGridItemSpan.FullLine
                                ) { PagingLoadingItem() }
                            }

                            is LoadState.Error -> {
                                val e = lazyPhotoItems.loadState.append as LoadState.Error
                                item(
                                    span = StaggeredGridItemSpan.FullLine
                                ) {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        onClickRetry = { retry() }
                                    )
                                }
                            }

                            else -> {
                                // do nothing
                            }
                        }
                    }
                }
            }
        }
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

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun PagingLoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}