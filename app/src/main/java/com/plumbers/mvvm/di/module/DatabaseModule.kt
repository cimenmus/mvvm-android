package com.plumbers.mvvm.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plumbers.mvvm.common.Constants
import com.plumbers.mvvm.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideAppDatabase(appDatabaseBuilder: RoomDatabase.Builder<AppDatabase?>) =
        appDatabaseBuilder.fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAppDatabaseBuilder(context: Context?) =
        Room.databaseBuilder(
            context!!,
            AppDatabase::class.java,
            Constants.Database.NAME)
}