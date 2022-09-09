package com.example.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor("apikey", API_KEY))
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    fun getSearchMovieCall(title: String, year: String, typeMovie: String): Call {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.omdbapi.com")
            .addQueryParameter("s", title)
            .addQueryParameter("y", year)
            .addQueryParameter("type", typeMovie)
            .build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}