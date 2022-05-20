package ru.sorokina.unsplash.modern.interactor.common.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.sorokina.unsplash.modern.BuildConfig
import java.io.IOException

private const val HEADER_AUTHORIZATION = "Authorization"
private const val CLIENT_ID = "Client-ID"

class ServiceInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headersBuilder = originalRequest.headers.newBuilder()
            .add(HEADER_AUTHORIZATION, "$CLIENT_ID ${BuildConfig.ACCESS_KEY}")

        return chain.proceed(
            originalRequest.newBuilder()
                .headers(headersBuilder.build())
                .build()
        )
    }
}