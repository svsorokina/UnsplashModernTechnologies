package ru.sorokina.unsplash.modern.ui.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.ui.common.image_loader.ImageLoader

@Composable
@Preview
fun PhotoDetailScreen(
    photo: Photo = Photo(),
    navigateBack: () -> Unit = {}
) {
    Box {
        ImageLoader(
            modifier = Modifier
                .fillMaxSize(),
            photo = photo,
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier.systemBarsPadding(),
            onClick = navigateBack
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}