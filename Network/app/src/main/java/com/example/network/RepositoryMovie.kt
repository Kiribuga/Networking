package com.example.network

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import kotlin.random.Random

class RepositoryMovie {

    fun searchMovie(
        title: String,
        year: String,
        typeMovie: String,
        callback: (List<RemoteMovie>) -> Unit,
        errorCallback: (e: Throwable) -> Unit
    ): Call {

        return Network.getSearchMovieCall(title, year, typeMovie).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.d("Server", "execute request error = ${e.message}", e)
                    errorCallback(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string()
                        val movies = parseMovieResponse(responseString!!)
                        callback(movies)
                    } else {
                        errorCallback(Throwable("Error response"))
                    }
                }
            })
        }
    }

    private fun parseMovieResponse(responseString: String): List<RemoteMovie> {
        return try {
            val jsonObject = JSONObject(responseString)
            val movieArray = jsonObject.getJSONArray("Search")
            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val idImdb = movieJsonObject.getString("imdbID")
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val typeMovie = movieJsonObject.getString("Type")
                    val poster = movieJsonObject.getString("Poster")

                    RemoteMovie(
                        id = Random.nextLong(),
                        idImdb = idImdb,
                        title = title,
                        typeMovie = typeMovie,
                        year = year,
                        poster = poster
                    )
                }
        } catch (e: JSONException) {
            Log.d("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }
}