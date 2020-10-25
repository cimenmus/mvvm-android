package com.plumbers.mvvm.di.module

import dagger.Module

@Module(
    includes = [
        ContextModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        DatabaseModule::class
    ]
)
class AppModule