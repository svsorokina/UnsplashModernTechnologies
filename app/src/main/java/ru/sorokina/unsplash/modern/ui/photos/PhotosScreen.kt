package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.ui.common.ErrorView
import ru.sorokina.unsplash.modern.ui.common.LoadingView
import ru.sorokina.unsplash.modern.ui.common.PagingItemErrorView
import ru.sorokina.unsplash.modern.ui.common.PagingLoadingItem
import ru.sorokina.unsplash.modern.ui.common.image_loader.ImageLoader

@Composable
fun PhotosScreen(
    photos: LazyPagingItems<Photo>,
    onPhotoClick: (String) -> Unit = {},
) {
    PhotosList(photos, onPhotoClick)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosList(
    lazyPhotoItems: LazyPagingItems<Photo>,
    onPhotoClick: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        when (lazyPhotoItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingView()
            }

            is LoadState.Error -> {
                val error = lazyPhotoItems.loadState.refresh as LoadState.Error
                ErrorView(
                    modifier = Modifier.align(Alignment.Center),
                    message = error.error.localizedMessage ?: stringResource(R.string.load_error),
                    onRetryClick = {
                        lazyPhotoItems.retry()
                    }
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
                        count = lazyPhotoItems.itemCount,
                        key = { index -> index }
                    ) { index ->
                        val modifier = when (index) {
                            0 -> Modifier.clip(RoundedCornerShape(topStart = 16.dp))
                            1 -> Modifier.clip(RoundedCornerShape(topEnd = 16.dp))
                            else -> Modifier
                        }
                        ItemPhoto(
                            modifier = modifier,
                            photo = lazyPhotoItems[index],
                            onPhotoClick = onPhotoClick
                        )
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
                                    PagingItemErrorView(
                                        message = e.error.localizedMessage
                                            ?: stringResource(R.string.load_error),
                                        onRetryClick = { retry() }
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
fun ItemPhoto(
    modifier: Modifier = Modifier,
    photo: Photo?,
    onPhotoClick: (String) -> Unit = {},
) {
    photo?.let {
        val aspectRatio = photo.width.toFloat() / photo.height.toFloat()

        ImageLoader(
            modifier = modifier
                .aspectRatio(aspectRatio)
                .clickable { onPhotoClick(photo.id) },
            photo = photo
        )
    }
}