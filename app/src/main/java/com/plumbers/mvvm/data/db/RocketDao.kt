package com.plumbers.mvvm.data.db

import androidx.room.*
import com.plumbers.mvvm.data.model.RocketModel

@Dao
interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(rocket: RocketModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(rockets: List<RocketModel>): List<Long>

    @Delete
    suspend fun delete(rocket: RocketModel)

    @Query("SELECT * FROM RocketModel")
    suspend fun getRockets(): List<RocketModel>
}