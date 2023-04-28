package ru.sorokina.unsplash.modern.interactor.photos

import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.photos.network.PhotosApi
import javax.inject.Inject
import javax.inject.Singleton

const val PER_PAGE_COUNT = 30
const val DEFAULT_PAGE = 1

@Singleton
class PhotosRepository @Inject constructor(
    private val photosApi: PhotosApi
) {
    suspend fun getPhotos(page: Int): List<Photo> {
        return photosApi.getPhotos(page = page, pageSize = PER_PAGE_COUNT).map {
            it.transform()
        }
    }
}