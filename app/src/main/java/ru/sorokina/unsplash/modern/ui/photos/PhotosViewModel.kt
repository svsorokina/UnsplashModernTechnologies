package ru.sorokina.unsplash.modern.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sorokina.unsplash.modern.App
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.photos.PER_PAGE_COUNT
import ru.sorokina.unsplash.modern.interactor.photos.PhotosInteractor
import ru.sorokina.unsplash.modern.interactor.photos.PhotosSource

class PhotosViewModel(
    private val photosInteractor: PhotosInteractor
) : ViewModel() {

    fun getPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PER_PAGE_COUNT),
            pagingSourceFactory = {
                PhotosSource(photosInteractor)
            }
        ).flow
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val photosInteractor = App.photosInteractor
                PhotosViewModel(photosInteractor)
            }
        }
    }
}