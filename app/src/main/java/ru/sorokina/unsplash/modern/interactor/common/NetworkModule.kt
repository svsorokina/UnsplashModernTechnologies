package ru.sorokina.unsplash.modern.interactor.common

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sorokina.unsplash.modern.interactor.common.interceptors.ServiceInterceptor
import java.util.concurrent.TimeUnit

private const val NETWORK_LOG_TAG = "NETWORK_LOG_TAG" //TODO add logger

class NetworkModule {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideHttpClient()) //TODO add DI
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(ServiceInterceptor())
            .addInterceptor(provideHttpLoggingInterceptor())
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message -> Log.d(NETWORK_LOG_TAG, message) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}