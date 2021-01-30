package com.plumbers.mvvm.data.api

import com.plumbers.mvvm.data.api.response.PopularMoviesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMoviesApiResponse
}
