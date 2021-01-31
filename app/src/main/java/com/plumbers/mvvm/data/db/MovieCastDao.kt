package com.plumbers.mvvm.data.db

import androidx.room.*
import com.plumbers.mvvm.data.model.MovieCastModel

@Dao
interface MovieCastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(cast: List<MovieCastModel>): List<Long>

    @Query("SELECT * FROM MovieCastModel WHERE movieId = :movieId ORDER BY `order` ASC")
    suspend fun getCastOfAMovie(movieId: Int): List<MovieCastModel>

    @Delete
    suspend fun delete(movieCast: MovieCastModel)
}