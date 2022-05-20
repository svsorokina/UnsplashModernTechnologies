package ru.sorokina.unsplash.modern.interactor.photos

import retrofit2.Retrofit
import ru.sorokina.unsplash.modern.interactor.photos.network.PhotosApi

class PhotosModule(private val retrofit: Retrofit) {

    fun providePhotosService(): PhotosApi =
        retrofit.create(PhotosApi::class.java)
}