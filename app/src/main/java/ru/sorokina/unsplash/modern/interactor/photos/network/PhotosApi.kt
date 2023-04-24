package ru.sorokina.unsplash.modern.interactor.photos.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.sorokina.unsplash.modern.interactor.common.PhotosApi.PHOTOS_URL
import ru.sorokina.unsplash.modern.interactor.photos.network.response.PhotoObj

interface PhotosApi {

    @GET(PHOTOS_URL)
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<PhotoObj>
}