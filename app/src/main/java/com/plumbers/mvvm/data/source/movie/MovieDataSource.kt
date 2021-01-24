package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.model.MovieModel

interface MovieDataSource {

    suspend fun getPopularMovies(page: Int): List<MovieModel>

    suspend fun saveMovies(movies: List<MovieModel>)
}