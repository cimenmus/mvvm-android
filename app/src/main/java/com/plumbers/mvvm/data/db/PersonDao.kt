package com.plumbers.mvvm.data.db

import androidx.room.*
import com.plumbers.mvvm.data.model.PersonModel

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(person: PersonModel)

    @Query("SELECT * FROM PersonModel WHERE id = :personId")
    suspend fun getPerson(personId: Int): PersonModel?

    @Delete
    suspend fun delete(person: PersonModel)
}