package ru.sorokina.unsplash.modern.interactor.photos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.common.NetworkModule
import ru.sorokina.unsplash.modern.interactor.photos.network.PhotosApi

class PhotosRepository(
    private val photosApi: PhotosApi = PhotosModule(NetworkModule().provideRetrofit()).providePhotosService()
) {
    suspend fun getPhotos(): Flow<List<Photo>> {
        return flow {
            val photos = photosApi.getPhotos().map {
                it.transform()
            }
            emit(photos)
        }.flowOn(Dispatchers.IO)
    }
}