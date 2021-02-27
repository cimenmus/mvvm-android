package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.data.repository.movie.MovieRepositoryImpl
import com.plumbers.mvvm.data.repository.person.PersonRepository
import com.plumbers.mvvm.data.repository.person.PersonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Suppress("unused")
@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindPersonRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository
}