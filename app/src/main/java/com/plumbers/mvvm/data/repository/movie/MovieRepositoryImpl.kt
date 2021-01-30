package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.common.util.NetworkUtils
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.di.annotation.qualifier.LocalMovieDataSource
import com.plumbers.mvvm.di.annotation.qualifier.RemoteMovieDataSource
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
    private val networkUtils: NetworkUtils,
    @RemoteMovieDataSource private val movieRemoteDataSource: MovieDataSource,
    @LocalMovieDataSource private val movieLocalDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): DataResult<List<MovieModel>> {
        val networkResult = movieRemoteDataSource.getPopularMovies(page = page)
        return if (networkResult is DataResult.Success) {
            saveMovies(movies = networkResult.data)
            networkResult
        } else {
            movieLocalDataSource.getPopularMovies(page = page)
        }
    }

    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieLocalDataSource.saveMovies(movies = movies)
    }
}
