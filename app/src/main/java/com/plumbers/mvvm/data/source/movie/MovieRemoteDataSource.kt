package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.common.AppError
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    MovieDataSource {

    override suspend fun getPopularMovies(page: Int): DataResult<List<MovieModel>> {
        apiService.getPopularMovies(page = page).apply {
            return when {
                isSuccess() -> DataResult.Success(results)
                else -> DataResult.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun saveMovies(movies: List<MovieModel>) = throw NotImplementedError()

    override suspend fun getCastOfAMovie(movieId: Int): DataResult<List<MovieCastModel>> {
        apiService.getCastOfAMovie(movieId = movieId).apply {
            return when {
                isSuccess() -> DataResult.Success(cast)
                else -> DataResult.Error(AppError(code = statusCode, message = getErrorMessage()))
            }
        }
    }

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) =
        throw NotImplementedError()
}
