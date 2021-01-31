package com.plumbers.mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.model.PersonModel

@Database(
    entities = [
        MovieModel::class,
        MovieCastModel::class,
        PersonModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieCastDao(): MovieCastDao
    abstract fun personDao(): PersonDao
}