package ru.sorokina.unsplash.modern.ui.common.image_loader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import com.ondev.imageblurkt_lib.AsyncImageBlurHash
import com.ondev.imageblurkt_lib.ImageBlurHashModel
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.domain.Photo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    photo: Photo?,
    contentScale: ContentScale = ContentScale.Fit
) {
    val model = ImageBlurHashModel(
        data = photo?.urls?.regular.orEmpty(),
        blurHash = photo?.blurHash.orEmpty()
    )
    AsyncImageBlurHash(
        modifier = modifier,
        model = model,
        notImageFoundRes = R.drawable.ic_image_not_found,
        contentScale = contentScale
    )
}