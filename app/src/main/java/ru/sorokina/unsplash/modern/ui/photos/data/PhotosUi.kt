package ru.sorokina.unsplash.modern.ui.photos.data

import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.common.Result

data class PhotosUi(
    val photos: Result<List<Photo>>
)
