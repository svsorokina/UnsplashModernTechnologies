package ru.sorokina.unsplash.modern.domain

import ru.sorokina.unsplash.modern.utils.Constants.EMPTY_STRING
import java.io.Serializable

data class Photo(
    val id: String = EMPTY_STRING,
    val blurHash: String = EMPTY_STRING,
    val width: Int = 0,
    val height: Int = 0,
    val urls: Urls = Urls(),
): Serializable

data class Urls(
    val full: String = EMPTY_STRING,
    val raw: String = EMPTY_STRING,
    val regular: String = EMPTY_STRING,
    val small: String = EMPTY_STRING,
    val smallS3: String = EMPTY_STRING,
    val thumb: String = EMPTY_STRING
): Serializable
