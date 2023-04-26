package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.ondev.imageblurkt_lib.AsyncImageBlurHash
import com.ondev.imageblurkt_lib.ImageBlurHashModel
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.ui.common.ErrorView
import ru.sorokina.unsplash.modern.ui.common.LoadingView
import ru.sorokina.unsplash.modern.ui.common.PagingItemErrorView
import ru.sorokina.unsplash.modern.ui.common.PagingLoadingItem

@Composable
@Preview
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

                        ImageLoader(lazyPhotoItems[index], modifier)
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageLoader(
    photo: Photo?,
    modifier: Modifier = Modifier
) {
    val model = ImageBlurHashModel(
        data = photo?.urls?.regular.orEmpty(),
        blurHash = photo?.blurHash.orEmpty()
    )

    val aspectRatio = photo?.height?.let {
        photo.width.toFloat() / photo.height.toFloat()
    }
    AsyncImageBlurHash(
        modifier = aspectRatio?.let {
            modifier.aspectRatio(it)
        } ?: modifier.fillMaxWidth(),
        model = model,
        notImageFoundRes = R.drawable.ic_image_not_found,
        contentScale = ContentScale.Fit
    )
}