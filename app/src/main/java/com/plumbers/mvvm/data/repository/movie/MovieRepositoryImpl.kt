package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.data.util.NetworkUtils
import com.plumbers.mvvm.data.result.NetworkBoundResult
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.di.qualifier.LocalMovieDataSource
import com.plumbers.mvvm.di.qualifier.RemoteMovieDataSource
import javax.inject.Inject
import com.plumbers.mvvm.data.result.Result

class MovieRepositoryImpl
@Inject constructor(
    private val networkUtils: NetworkUtils,
    @RemoteMovieDataSource private val movieRemoteDataSource: MovieDataSource,
    @LocalMovieDataSource private val movieLocalDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> =
        object : NetworkBoundResult<List<MovieModel>>() {

            override fun isOnline(): Boolean = networkUtils.isNetworkAvailable()

            override suspend fun loadFromNetwork(): Result<List<MovieModel>> =
                movieRemoteDataSource.getPopularMovies(page = page)

            override suspend fun loadFromDb(): Result<List<MovieModel>> =
                movieLocalDataSource.getPopularMovies(page = page)

            override suspend fun saveNetworkResult(data: List<MovieModel>) =
                saveMovies(movies = data)
        }.execute()

    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieLocalDataSource.saveMovies(movies = movies)
    }

    override suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>> =
        object : NetworkBoundResult<List<MovieCastModel>>() {

            override fun isOnline(): Boolean = networkUtils.isNetworkAvailable()

            override suspend fun loadFromNetwork(): Result<List<MovieCastModel>> =
                movieRemoteDataSource.getCastOfMovie(movieId = movieId)

            override suspend fun loadFromDb(): Result<List<MovieCastModel>> =
                movieLocalDataSource.getCastOfMovie(movieId = movieId)

            override suspend fun saveNetworkResult(data: List<MovieCastModel>) =
                saveMovieCast(movieId = movieId, movieCast = data)
        }.execute()

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) =
        movieLocalDataSource.saveMovieCast(movieId = movieId, movieCast = movieCast)
}
