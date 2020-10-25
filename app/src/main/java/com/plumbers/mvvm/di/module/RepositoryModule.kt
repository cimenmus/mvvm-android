package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.data.repository.rocket.RocketRepository
import com.plumbers.mvvm.data.repository.rocket.RocketRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRocketRepository(rocketRepositoryImpl: RocketRepositoryImpl): RocketRepository
}