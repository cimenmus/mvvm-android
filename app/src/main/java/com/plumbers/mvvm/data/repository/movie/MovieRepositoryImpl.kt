package com.plumbers.mvvm.data.repository.movie

import com.plumbers.mvvm.common.util.NetworkUtils
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.source.movie.MovieDataSource
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl
@Inject constructor(private val networkUtils: NetworkUtils,
                    @Named("remote")private val movieRemoteDataSource: MovieDataSource,
                    @Named("local")private val movieLocalDataSource: MovieDataSource): MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<MovieModel> =
        if(networkUtils.isNetworkAvailable()){
            movieRemoteDataSource.getPopularMovies(page = page)
        } else {
            movieLocalDataSource.getPopularMovies(page = page)
        }


    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieLocalDataSource.saveMovies(movies = movies)
    }
}