package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
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
import ru.sorokina.unsplash.modern.ui.shimmerEffect
import kotlin.random.Random

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadingView() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
    ) {
        items(count = 12) { index: Int ->
            val modifier = when (index) {
                0 -> Modifier.clip(RoundedCornerShape(topStart = 16.dp))
                1 -> Modifier.clip(RoundedCornerShape(topEnd = 16.dp))
                else -> Modifier
            }

            val height = Random.nextInt(96, 224)
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .background(
                        colorResource(id = R.color.gray)
                    )
                    .shimmerEffect()
            )
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