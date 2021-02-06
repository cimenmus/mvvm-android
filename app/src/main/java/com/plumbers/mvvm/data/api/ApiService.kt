package com.plumbers.mvvm.data.api

import com.plumbers.mvvm.data.api.response.MovieCastApiResponse
import com.plumbers.mvvm.data.api.response.PopularMoviesApiResponse
import com.plumbers.mvvm.data.model.PersonModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.plumbers.mvvm.data.result.Result

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Result<PopularMoviesApiResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getCastOfAMovie(@Path("movieId") movieId: Int): Result<MovieCastApiResponse>

    @GET("person/{personId}")
    suspend fun getPersonDetails(@Path("personId") personId: Int): Result<PersonModel>
}
