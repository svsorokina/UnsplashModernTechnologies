package ru.sorokina.unsplash.modern

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.sorokina.unsplash.modern.domain.Photo
import ru.sorokina.unsplash.modern.interactor.common.Result
import ru.sorokina.unsplash.modern.interactor.photos.PhotosInteractor
import ru.sorokina.unsplash.modern.utils.Logger
import timber.log.Timber

class App : Application() {

    private val photosInteractor = PhotosInteractor()

    override fun onCreate() {
        super.onCreate()
        initTimber()
        fetchData()
    }

    private fun fetchData() {
        //TODO just for test
        GlobalScope.launch {
            photosInteractor.getPhotos().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val photos: List<Photo> = result.data
                        Logger.d("GET RESULT $photos")
                    }
                    is Result.Error -> {
                        Logger.e("ERROR", result.exception)
                    }
                    is Result.Loading -> {
                        Logger.d("LOADING")
                    }
                }
            }
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //log to remote logger
        }
    }
}