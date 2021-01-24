package com.plumbers.mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plumbers.mvvm.data.model.MovieModel

@Database(
    entities = [
        MovieModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}