package ru.sorokina.unsplash.modern.domain

import ru.sorokina.unsplash.modern.utils.Constants.EMPTY_STRING

data class Photo(
    val id: String = EMPTY_STRING,
    val blurHash: String,
    val width: Int,
    val height: Int,
    val urls: Urls = Urls(),
)

data class Urls(
    val full: String = EMPTY_STRING,
    val raw: String = EMPTY_STRING,
    val regular: String = EMPTY_STRING,
    val small: String = EMPTY_STRING,
    val smallS3: String = EMPTY_STRING,
    val thumb: String = EMPTY_STRING
)
