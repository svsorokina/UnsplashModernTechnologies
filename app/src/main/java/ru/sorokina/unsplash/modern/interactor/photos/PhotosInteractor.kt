package ru.sorokina.unsplash.modern.interactor.photos

import ru.sorokina.unsplash.modern.domain.Photo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosInteractor @Inject constructor(
    private val photosRepository: PhotosRepository
) {
    suspend fun getPhotos(page: Int = DEFAULT_PAGE): List<Photo> {
        return photosRepository.getPhotos(page)
    }
}