package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel

interface MovieDataSource {

    suspend fun getPopularMovies(page: Int): Result<List<MovieModel>>

    suspend fun saveMovies(movies: List<MovieModel>)

    suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>>

    suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>)
}