package com.example.network

data class RemoteMovie(
    val id: Long,
    val idImdb: String,
    val title: String,
    val typeMovie: String,
    val year: String,
    val poster: String
)
