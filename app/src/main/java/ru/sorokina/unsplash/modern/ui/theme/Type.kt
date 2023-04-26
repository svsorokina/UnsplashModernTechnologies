package ru.sorokina.unsplash.modern.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ru.sorokina.unsplash.modern.R

private val regular = Font(R.font.amiko_regular, FontWeight.W400)
private val medium = Font(R.font.amiko_semi_bold, FontWeight.W500)
private val bold = Font(R.font.amiko_bold, FontWeight.W700)

val fontFamily = FontFamily(fonts = listOf(regular, medium, bold))

val Typography = Typography(
    defaultFontFamily = fontFamily
)