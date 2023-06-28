package com.example.omdb.model.network

import com.example.omdb.model.repository.MovieRepository
import com.example.omdb.model.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieRepositoryModule {
    @Binds
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}