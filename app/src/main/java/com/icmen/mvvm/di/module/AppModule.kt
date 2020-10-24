package com.icmen.mvvm.di.module

import dagger.Module

@Module(
    includes = [
        ContextModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
class AppModule