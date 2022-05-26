package ru.sorokina.unsplash.modern.interactor.photos.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.domain.Urls
import ru.sorokina.unsplash.modern.interactor.common.Transformable

@Serializable
data class PhotoObj(
    @SerialName("id")
    val id: String?,
    @SerialName("urls")
    val urls: UrlsObj?
) : Transformable<Photo> {

    override fun transform(): Photo {
        return Photo(
            id = id.orEmpty(),
            urls = urls?.transform() ?: Urls()
        )
    }
}

@Serializable
data class UrlsObj(
    @SerialName("full")
    val full: String?,
    @SerialName("raw")
    val raw: String?,
    @SerialName("regular")
    val regular: String?,
    @SerialName("small")
    val small: String?,
    @SerialName("small_s3")
    val smallS3: String?,
    @SerialName("thumb")
    val thumb: String?
) : Transformable<Urls> {

    override fun transform(): Urls {
        return Urls(
            full = full.orEmpty(),
            raw = raw.orEmpty(),
            regular = regular.orEmpty(),
            small = small.orEmpty(),
            smallS3 = smallS3.orEmpty(),
            thumb = thumb.orEmpty()
        )
    }
}