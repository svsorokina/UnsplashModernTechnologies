package ru.sorokina.unsplash.modern.interactor.photos.network

import retrofit2.http.GET
import ru.sorokina.unsplash.modern.interactor.common.PhotosApi.PHOTOS_URL
import ru.sorokina.unsplash.modern.interactor.photos.network.response.PhotoObj

interface PhotosApi {

    @GET(PHOTOS_URL)
    suspend fun getPhotos(): List<PhotoObj>
}