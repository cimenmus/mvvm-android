package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.data.source.movie.MovieLocalDataSource
import com.plumbers.mvvm.data.source.movie.MovieRemoteDataSource
import com.plumbers.mvvm.di.annotation.qualifier.LocalMovieDataSource
import com.plumbers.mvvm.di.annotation.qualifier.RemoteMovieDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Suppress("unused")
@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Binds
    @RemoteMovieDataSource
    abstract fun bindMovieRemoteData(movieRemoteDataSourceQualifier: MovieRemoteDataSource): MovieDataSource

    @Binds
    @LocalMovieDataSource
    abstract fun bindMovieLocalData(sampleLocalDataSourceQualifier: MovieLocalDataSource): MovieDataSource
}

