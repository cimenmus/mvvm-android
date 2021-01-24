package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(): MovieDataSource {

    override suspend fun getPopularMovies(page: Int): List<MovieModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveMovies(movies: List<MovieModel>) {
        TODO("Not yet implemented")
    }

}