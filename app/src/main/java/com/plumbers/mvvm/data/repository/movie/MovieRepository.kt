package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): DataResult<List<MovieModel>>

    suspend fun saveMovies(movies: List<MovieModel>)

    suspend fun getCastOfAMovie(movieId: Int): DataResult<List<MovieCastModel>>

    suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>)
}