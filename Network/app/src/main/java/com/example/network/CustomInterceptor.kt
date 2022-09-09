package com.example.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(
    private val paramName: String,
    private val paramValue: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlOriginal: HttpUrl = chain.request().url
        val newUrl = urlOriginal.newBuilder()
            .addQueryParameter(paramName, paramValue)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}