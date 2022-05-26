package ru.sorokina.unsplash.modern.utils

import android.util.Log
import timber.log.Timber

object Logger {

    fun d(message: String?, t: Throwable? = null, vararg args: Any?) {
        Timber.log(Log.DEBUG, t, message, *args)
    }

    fun e(message: String?, t: Throwable? = null, vararg args: Any?) {
        Timber.log(Log.ERROR, t, message, *args)
    }
}