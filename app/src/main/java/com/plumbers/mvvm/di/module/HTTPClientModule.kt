package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.BuildConfig
import com.plumbers.mvvm.data.api.DefaultRequestInterceptor
import com.plumbers.mvvm.di.qualifier.DefaultInterceptor
import com.plumbers.mvvm.di.qualifier.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class HTTPClientModule {

    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()

    @Provides
    fun provideOkHttpBuilder(
        @DefaultInterceptor requestInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        timeout: Int
    ): OkHttpClient.Builder {
        val builder =
            OkHttpClient.Builder()
                .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(requestInterceptor)
                .followRedirects(true)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder
    }

    @Provides
    @DefaultInterceptor
    fun provideDefaultRequestInterceptor(): Interceptor = DefaultRequestInterceptor()

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideTimeout() = 15
}
