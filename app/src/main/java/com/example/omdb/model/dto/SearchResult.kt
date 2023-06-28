package com.example.omdb.model.dto

data class SearchResult(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)