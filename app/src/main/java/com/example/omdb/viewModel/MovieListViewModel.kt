package com.example.omdb.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.model.dto.Movie
import com.example.omdb.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList<Movie>())
    val movieList get() = _movieList.asStateFlow()

    private val _networkError = MutableSharedFlow<String>()
    val networkError get() = _networkError.asSharedFlow()

    fun searchMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieRepository.searchMovie(query)
                response?.let {
                    _movieList.emit(response.Search)
                } ?: showError("Empty Movie List")
            } catch (e: Exception) {
                // show error.
                showError(e.message.toString())
            }
        }
    }

    private suspend fun showError(error: String) {
        _networkError.emit(error)
    }
}