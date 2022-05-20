package ru.sorokina.unsplash.modern.interactor.photos

import kotlinx.coroutines.flow.Flow

class PhotosInteractor(
    private val photosRepository: PhotosRepository = PhotosRepository()
) {
    suspend fun getPhotos(): Flow<List<Unit>> {
        return photosRepository.getPhotos()
    }
}