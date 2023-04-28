package ru.sorokina.unsplash.modern.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int = 0,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = stringResource(text),
            fontSize = 16.sp
        )
    }
) {
    Button(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        colors = colors,
        content = content
    )
}

@Composable
fun RoundedIconButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int = 0,
    @DrawableRes icon: Int = 0,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = stringResource(text),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            modifier = Modifier.padding(PaddingValues(
                bottom = 4.dp
            )),
            painter = painterResource(id = icon),
            contentDescription = ""
        )
    }
) {
    RoundedButton(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
        content = content
    )
}