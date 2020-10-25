package com.plumbers.mvvm.data.db

import androidx.room.*
import com.plumbers.mvvm.data.model.RocketModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(rocket: RocketModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(rockets: List<RocketModel>): List<Long>

    @Delete
    suspend fun delete(rocket: RocketModel)

    /**
     * Using a coroutine Flow as a return type, you will get this error
     * (Not sure how to convert a Cursor to this method's return type ..)
     * if you accidentally make the function suspend.
     * Since it is returning a flow, there is no need to suspend.
     */
    @Query("SELECT * FROM RocketModel")
    fun getRockets(): Flow<List<RocketModel>>
}