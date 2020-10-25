package com.plumbers.mvvm.di.module

import dagger.Module

@Module(
    includes = [
        ContextModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
class AppModule