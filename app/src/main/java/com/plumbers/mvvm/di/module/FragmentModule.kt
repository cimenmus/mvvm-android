package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.di.annotation.FragmentScope
import com.plumbers.mvvm.ui.movie.moviedetail.MovieDetailsFragment
import com.plumbers.mvvm.ui.movie.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment
}