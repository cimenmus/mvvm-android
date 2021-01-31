package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.data.source.movie.MovieLocalDataSource
import com.plumbers.mvvm.data.source.movie.MovieRemoteDataSource
import com.plumbers.mvvm.data.source.person.PersonDataSource
import com.plumbers.mvvm.data.source.person.PersonLocalDataSource
import com.plumbers.mvvm.data.source.person.PersonRemoteDataSource
import com.plumbers.mvvm.di.qualifier.LocalMovieDataSource
import com.plumbers.mvvm.di.qualifier.LocalPersonDataSource
import com.plumbers.mvvm.di.qualifier.RemoteMovieDataSource
import com.plumbers.mvvm.di.qualifier.RemotePersonDataSource
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
    abstract fun bindMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSource): MovieDataSource

    @Binds
    @LocalMovieDataSource
    abstract fun bindMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSource): MovieDataSource

    @Binds
    @RemotePersonDataSource
    abstract fun bindPersonRemoteDataSource(personRemoteDataSource: PersonRemoteDataSource): PersonDataSource

    @Binds
    @LocalPersonDataSource
    abstract fun bindPersonLocalDataSource(personLocalDataSource: PersonLocalDataSource): PersonDataSource
}

