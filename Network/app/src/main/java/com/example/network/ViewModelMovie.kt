package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call

class ViewModelMovie : ViewModel() {

    private var currentCall: Call? = null

    private val repository = RepositoryMovie()

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()

    private val isLoadingLiveData = MutableLiveData<Boolean>()

    private val errorDownloadLiveData = MutableLiveData<Boolean>()

    private val errorMessageLiveData = MutableLiveData<String>()

    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val errorDownload: LiveData<Boolean>
        get() = errorDownloadLiveData

    val errorMessage: LiveData<String>
        get() = errorMessageLiveData

    fun search(title: String, year: String, typeMovie: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(title, year, typeMovie, { movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null
        }) { error ->
            if (error == Throwable(message = null)) {
                errorDownloadLiveData.postValue(false)
            } else {
                errorDownloadLiveData.postValue(true)
                errorMessageLiveData.postValue("${error.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}