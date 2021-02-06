package com.plumbers.mvvm.data.db

import androidx.room.*
import com.plumbers.mvvm.data.Constants
import com.plumbers.mvvm.data.model.MovieModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(movies: List<MovieModel>)

    @Query("SELECT * FROM MovieModel ORDER BY popularity DESC LIMIT ${Constants.Movie.PAGE_LIMIT} OFFSET :offset")
    suspend fun getPopularMovies(offset: Int): List<MovieModel>

    @Delete
    suspend fun delete(movie: MovieModel)
}