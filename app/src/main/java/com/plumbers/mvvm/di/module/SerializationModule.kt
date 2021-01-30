package com.plumbers.mvvm.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.plumbers.mvvm.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("SpellCheckingInspection")
@Module
@InstallIn(ApplicationComponent::class)
class SerializationModule {

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideGson(gsonBuilder: GsonBuilder): Gson = gsonBuilder.create()

    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
        if (BuildConfig.DEBUG) {
            gsonBuilder.setPrettyPrinting()
        }
        return gsonBuilder
    }
}
