package ru.sorokina.unsplash.modern.interactor.photos

import ru.sorokina.unsplash.modern.domain.Photo

class PhotosInteractor(
    private val photosRepository: PhotosRepository = PhotosRepository()
) {
    suspend fun getPhotos(page: Int = DEFAULT_PAGE): List<Photo> {
        return photosRepository.getPhotos(page)
    }
}