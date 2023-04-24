package ru.sorokina.unsplash.modern.interactor.photos

import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.common.NetworkModule
import ru.sorokina.unsplash.modern.interactor.photos.network.PhotosApi

const val PER_PAGE_COUNT = 30
const val DEFAULT_PAGE = 1

class PhotosRepository(
    private val photosApi: PhotosApi = PhotosModule(NetworkModule().provideRetrofit()).providePhotosService()
) {
    suspend fun getPhotos(page: Int): List<Photo> {
        return photosApi.getPhotos(page = page, pageSize = PER_PAGE_COUNT).map {
            it.transform()
        }
    }
}