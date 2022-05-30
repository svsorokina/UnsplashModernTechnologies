package ru.sorokina.unsplash.modern

import android.app.Application
import ru.sorokina.unsplash.modern.interactor.photos.PhotosInteractor
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //log to remote logger
        }
    }

    companion object {
        //TODO add DI

        val photosInteractor = PhotosInteractor()
    }
}