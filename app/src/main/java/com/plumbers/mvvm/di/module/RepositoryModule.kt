package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.data.repository.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}