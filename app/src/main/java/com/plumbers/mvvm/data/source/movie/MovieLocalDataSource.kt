package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.Constants
import com.plumbers.mvvm.data.result.DatabaseResource
import com.plumbers.mvvm.data.db.MovieCastDao
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject
import com.plumbers.mvvm.data.result.Result

class MovieLocalDataSource
@Inject constructor(
    private val movieDao: MovieDao,
    private val movieCastDao: MovieCastDao
) : MovieDataSource {

    override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> =
        object : DatabaseResource<List<MovieModel>>() {
            override suspend fun load(): List<MovieModel> {
                val offset = (page - 1) * Constants.Movie.PAGE_LIMIT
                return movieDao.getPopularMovies(offset = offset)
            }
        }.execute()

    override suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>> =
        object : DatabaseResource<List<MovieCastModel>>() {
            override suspend fun load(): List<MovieCastModel> {
                return movieCastDao.getCastOfMovie(movieId = movieId)
            }
        }.execute()

    override suspend fun saveMovies(movies: List<MovieModel>) =
        movieDao.add(movies = movies)

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) {
        movieCast.forEach { it.movieId = movieId }
        movieCastDao.add(cast = movieCast)
    }
}
