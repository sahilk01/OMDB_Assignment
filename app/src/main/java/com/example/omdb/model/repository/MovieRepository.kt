package com.example.omdb.model.repository

import com.example.omdb.model.dto.SearchResult
import com.example.omdb.model.dto.movieDetail.MovieDetail

interface MovieRepository {
    suspend fun searchMovie(query: String): SearchResult?

    suspend fun getMovieById(id: String): MovieDetail?
}