package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.util.NetworkUtils
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.di.qualifier.LocalMovieDataSource
import com.plumbers.mvvm.di.qualifier.RemoteMovieDataSource
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
    private val networkUtils: NetworkUtils,
    @RemoteMovieDataSource private val movieRemoteDataSource: MovieDataSource,
    @LocalMovieDataSource private val movieLocalDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> {
        val networkResult = movieRemoteDataSource.getPopularMovies(page = page)
        return if (networkResult is Result.Success) {
            saveMovies(movies = networkResult.data)
            networkResult
        } else {
            movieLocalDataSource.getPopularMovies(page = page)
        }
    }

    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieLocalDataSource.saveMovies(movies = movies)
    }

    override suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>> {
        val networkResult = movieRemoteDataSource.getCastOfMovie(movieId = movieId)
        return if (networkResult is Result.Success) {
            saveMovieCast(movieId = movieId, movieCast =  networkResult.data)
            networkResult
        } else {
            movieLocalDataSource.getCastOfMovie(movieId = movieId)
        }
    }

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) {
        movieLocalDataSource.saveMovieCast(movieId = movieId, movieCast = movieCast)
    }
}
