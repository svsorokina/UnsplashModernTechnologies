package ru.sorokina.unsplash.modern.ui.photos

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.photos.PER_PAGE_COUNT
import ru.sorokina.unsplash.modern.interactor.photos.PhotosInteractor
import ru.sorokina.unsplash.modern.interactor.photos.PhotosSource
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
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
}