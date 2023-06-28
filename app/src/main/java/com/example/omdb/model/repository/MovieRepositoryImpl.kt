package com.example.omdb.model.repository

import com.example.omdb.model.dto.SearchResult
import com.example.omdb.model.dto.movieDetail.MovieDetail
import com.example.omdb.model.network.MovieApi
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
): MovieRepository {

    override suspend fun searchMovie(query: String): SearchResult? {
        return movieApi.search(query).body()
    }

    override suspend fun getMovieById(id: String): MovieDetail? {
        return movieApi.getMovieById(id).body()
    }
}