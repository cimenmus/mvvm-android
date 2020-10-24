package com.icmen.mvvm.di.module

import android.app.Application
import android.content.Context
import com.icmen.mvvm.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Singleton
    @Provides
    fun provideApplicationContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideApplication(app: Application) = app as App
}