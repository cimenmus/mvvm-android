package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.MovieModel

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): DataResult<List<MovieModel>>

    suspend fun saveMovies(movies: List<MovieModel>)
}