package ru.sorokina.unsplash.modern.ui.photo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.ui.common.RoundedButton
import ru.sorokina.unsplash.modern.ui.common.RoundedIconButton
import ru.sorokina.unsplash.modern.ui.common.image_loader.ImageLoader
import ru.sorokina.unsplash.modern.ui.theme.UnsplashTheme

@Composable
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(PaddingValues(24.dp))
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RoundedButton(
                modifier = Modifier.weight(1.0f),
                text = R.string.about_photo_btn_text,
                onClick = { /*TODO*/ }
            )
            RoundedIconButton(
                modifier = Modifier.weight(1.0f),
                text = R.string.share_btn_text,
                icon = R.drawable.ic_share,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = colorResource(id = R.color.vegan_mastermind)
                ),
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Preview
@Composable
fun PhotoDetailScreenPreview() {
    UnsplashTheme {
        PhotoDetailScreen()
    }
}