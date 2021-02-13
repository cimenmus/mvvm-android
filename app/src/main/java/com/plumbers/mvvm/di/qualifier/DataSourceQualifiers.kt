package com.plumbers.mvvm.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteMovieDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalMovieDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemotePersonDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalPersonDataSource