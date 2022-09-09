package com.example.network

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.network.adapter.AdapterMovie
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.listMovie

class MainFragment : Fragment(R.layout.fragment_main) {

    private var movieAdapter: AdapterMovie by autoCleared()
    private val viewModelMovie: ViewModelMovie by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayStringMovie = resources.getStringArray(R.array.type_movie)
        val adapterDropdownMenu =
            ArrayAdapter(requireContext(), R.layout.item_list_menu, arrayStringMovie)
        autoComplete.setAdapter(adapterDropdownMenu)
        initList()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        val arrayStringMovie = resources.getStringArray(R.array.type_movie)
        val adapterDropdownMenu =
            ArrayAdapter(requireContext(), R.layout.item_list_menu, arrayStringMovie)
        autoComplete.setAdapter(adapterDropdownMenu)
    }

    private fun initList() {
        movieAdapter = AdapterMovie()
        with(listMovie) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        searchButton.setOnClickListener {
            viewModelMovie.search(
                titleMovie.text.toString(),
                yearMovie.text.toString(),
                autoComplete.text.toString()
            )

        }

        retryButton.setOnClickListener {
            viewModelMovie.search(
                titleMovie.text.toString(),
                yearMovie.text.toString(),
                autoComplete.text.toString()
            )
            errorTextView.isVisible = false
            retryButton.isVisible = false
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModelMovie.isLoading.observe(viewLifecycleOwner, ::updateLoading)

        viewModelMovie.movieList.observe(viewLifecycleOwner) { movies ->
            Log.d("viewModelMovie", "$movies")
            result(movies)
        }

        viewModelMovie.errorMessage.observe(viewLifecycleOwner) { message ->
            errorTextView.text = message
        }

        viewModelMovie.errorDownload.observe(viewLifecycleOwner) { error ->
            if (viewModelMovie.errorDownload.value == error) {
                progressBar.isVisible = false
                errorTextView.isVisible = true
                retryButton.isVisible = true
            }
        }
    }

    private fun result(listMovies: List<RemoteMovie>) {
        if (listMovies.isNotEmpty()){
            movieAdapter.items = listMovies
            resultSearch.isVisible = true
            errorSearch.isVisible = false
        } else {
            errorSearch.isVisible = true
            resultSearch.isVisible = false
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
        titleMovie.isEnabled = isLoading.not()
        yearMovie.isEnabled = isLoading.not()
        dropdownMovie.isEnabled = isLoading.not()
        searchButton.isEnabled = isLoading.not()
    }
}