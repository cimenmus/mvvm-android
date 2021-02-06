package com.plumbers.mvvm.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plumbers.mvvm.data.Constants
import com.plumbers.mvvm.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()

    @Provides
    fun provideMovieCastDao(appDatabase: AppDatabase) = appDatabase.movieCastDao()

    @Provides
    fun providePersonDao(appDatabase: AppDatabase) = appDatabase.personDao()

    @Provides
    fun provideAppDatabase(appDatabaseBuilder: RoomDatabase.Builder<AppDatabase?>) =
        appDatabaseBuilder.fallbackToDestructiveMigration().build()

    @Provides
    fun provideAppDatabaseBuilder(@ApplicationContext context: Context?) =
        Room.databaseBuilder(
            context!!,
            AppDatabase::class.java,
            Constants.Database.NAME)
}