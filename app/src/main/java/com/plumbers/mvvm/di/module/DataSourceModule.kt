package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.data.source.movie.MovieLocalDataSource
import com.plumbers.mvvm.data.source.movie.MovieRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class DataSourceModule {

    @Singleton
    @Named("remote")
    @Binds
    abstract fun provideMovieRemoteSource(movieRemoteDataSource: MovieRemoteDataSource): MovieDataSource

    @Singleton
    @Named("local")
    @Binds
    abstract fun provideMovieLocalSource(movieLocalDataSource: MovieLocalDataSource): MovieDataSource
}