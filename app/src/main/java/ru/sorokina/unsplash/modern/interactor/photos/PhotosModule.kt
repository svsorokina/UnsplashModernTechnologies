package ru.sorokina.unsplash.modern.interactor.photos

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.sorokina.unsplash.modern.interactor.photos.network.PhotosApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhotosModule {

    @Singleton
    @Provides
    fun providePhotosService(retrofit: Retrofit): PhotosApi =
        retrofit.create(PhotosApi::class.java)
}