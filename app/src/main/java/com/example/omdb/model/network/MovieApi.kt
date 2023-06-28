package com.example.omdb.model.network

import com.example.omdb.model.dto.SearchResult
import com.example.omdb.model.dto.movieDetail.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(NetworkConstants.BASE_URL)
    suspend fun search(
        @Query("s") query: String
    ): Response<SearchResult>

    @GET(NetworkConstants.BASE_URL)
    suspend fun getMovieById(
        @Query("i") id: String
    ): Response<MovieDetail>

}