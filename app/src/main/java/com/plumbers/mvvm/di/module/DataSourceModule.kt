package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.source.rocket.RocketDataSource
import com.plumbers.mvvm.data.source.rocket.RocketLocalDataSource
import com.plumbers.mvvm.data.source.rocket.RocketRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class DataSourceModule {

    @Singleton
    @Named("remote")
    @Binds
    abstract fun provideRocketRemoteSource(rocketRemoteDataSource: RocketRemoteDataSource): RocketDataSource

    @Singleton
    @Named("local")
    @Binds
    abstract fun provideRocketLocalSource(rocketLocalDataSource: RocketLocalDataSource): RocketDataSource
}