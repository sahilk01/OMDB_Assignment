package com.example.omdb.model.network

sealed class NetworkResult {
    data class Success<T>(val data: T): NetworkResult()
    data class Failure(val error: String): NetworkResult()
}
