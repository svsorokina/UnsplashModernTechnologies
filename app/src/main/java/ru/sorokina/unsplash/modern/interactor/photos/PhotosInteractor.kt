package ru.sorokina.unsplash.modern.interactor.photos

import kotlinx.coroutines.flow.Flow
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.common.Result
import ru.sorokina.unsplash.modern.interactor.common.asResult

class PhotosInteractor(
    private val photosRepository: PhotosRepository = PhotosRepository()
) {
    suspend fun getPhotos(): Flow<Result<List<Photo>>> {
        return photosRepository.getPhotos().asResult()
    }
}