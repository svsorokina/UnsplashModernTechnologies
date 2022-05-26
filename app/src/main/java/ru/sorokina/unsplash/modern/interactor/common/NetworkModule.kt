package ru.sorokina.unsplash.modern.interactor.common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.sorokina.unsplash.modern.interactor.common.interceptors.ServiceInterceptor
import ru.sorokina.unsplash.modern.utils.Logger
import java.util.concurrent.TimeUnit

class NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .client(provideHttpClient()) //TODO add DI
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
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
        val logging = HttpLoggingInterceptor { message -> Logger.d(message) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}