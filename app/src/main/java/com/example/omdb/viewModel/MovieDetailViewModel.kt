package com.example.omdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.model.dto.movieDetail.MovieDetail
import com.example.omdb.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _networkFailure = MutableSharedFlow<String>()
    val networkFailure get() = _networkFailure

    fun getMovieById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieRepository.getMovieById(id)
                response?.let {
                    _movieDetail.emit(it)
                } ?: showError("Empty Movie List")
            } catch (e: Exception) {
                // show error.
                showError(e.message.toString())
            }
        }
    }

    private suspend fun showError(error: String) {
        _networkFailure.emit(error)
    }
}