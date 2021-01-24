package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.data.model.MovieModel

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): List<MovieModel>

    suspend fun saveMovies(movies: List<MovieModel>)
}