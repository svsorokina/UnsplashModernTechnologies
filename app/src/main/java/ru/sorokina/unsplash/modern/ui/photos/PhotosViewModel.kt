package ru.sorokina.unsplash.modern.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.sorokina.unsplash.modern.App
import ru.sorokina.unsplash.modern.interactor.common.Result
import ru.sorokina.unsplash.modern.ui.photos.data.PhotosUi
import ru.sorokina.unsplash.modern.utils.Logger

class PhotosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PhotosUi(Result.Loading))
    val uiState: StateFlow<PhotosUi> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PhotosUi(Result.Loading)
    )

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            App.photosInteractor.getPhotos().collect {
                _uiState.value = PhotosUi(it)
            }
        }
    }
}