package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.common.AppError
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    MovieDataSource {

    override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> {
        apiService.getPopularMovies(page = page).apply {
            return when {
                isSuccess() -> Result.Success(results)
                else -> Result.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun saveMovies(movies: List<MovieModel>) = throw NotImplementedError()

    override suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>> {
        apiService.getCastOfAMovie(movieId = movieId).apply {
            return when {
                isSuccess() -> Result.Success(cast)
                else -> Result.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) =
        throw NotImplementedError()
}
