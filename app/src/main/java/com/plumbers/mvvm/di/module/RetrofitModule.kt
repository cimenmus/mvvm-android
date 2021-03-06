package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.BuildConfig
import com.plumbers.mvvm.data.api.base.SuspendCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("SpellCheckingInspection")
@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        suspendCallAdapterFactory: SuspendCallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(suspendCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideCallAdapterFactory() = SuspendCallAdapterFactory()
}
