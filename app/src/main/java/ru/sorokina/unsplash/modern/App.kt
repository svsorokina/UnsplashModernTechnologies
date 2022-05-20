package ru.sorokina.unsplash.modern

import android.app.Application
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.sorokina.unsplash.modern.interactor.photos.PhotosInteractor

class App : Application() {

    private val photosInteractor = PhotosInteractor()

    override fun onCreate() {
        super.onCreate()
        fetchData()
    }

    private fun fetchData() {
        //TODO just for test
        //TODO handle error
        GlobalScope.launch {
            photosInteractor.getPhotos().collect {
                Log.d("COROUTINE_TEST", "GET RESULT")
            }
        }
    }
}