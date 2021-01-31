package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.common.Constants
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.db.MovieCastDao
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject

class MovieLocalDataSource
@Inject constructor(
    private val movieDao: MovieDao,
    private val movieCastDao: MovieCastDao
) : MovieDataSource {

    override suspend fun getPopularMovies(page: Int): DataResult<List<MovieModel>> {
        val offset = (page - 1) * Constants.Movie.PAGE_LIMIT
        return DataResult.Success(movieDao.getPopularMovies(offset = offset))
    }

    override suspend fun saveMovies(movies: List<MovieModel>) {
        movieDao.add(movies = movies)
    }

    override suspend fun getCastOfAMovie(movieId: Int): DataResult<List<MovieCastModel>> =
        DataResult.Success(movieCastDao.getCastOfAMovie(movieId = movieId))

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) {
        movieCast.forEach { it.movieId = movieId }
        movieCastDao.add(cast = movieCast)
    }
}
