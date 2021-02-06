package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.result.NetworkResult
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.api.response.MovieCastApiResponse
import com.plumbers.mvvm.data.api.response.PopularMoviesApiResponse
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import javax.inject.Inject
import com.plumbers.mvvm.data.result.Result

class MovieRemoteDataSource
@Inject constructor(private val apiService: ApiService) : MovieDataSource {

    override suspend fun getPopularMovies(page: Int): Result<List<MovieModel>> =
        object : NetworkResult<PopularMoviesApiResponse, List<MovieModel>>() {
            override suspend fun load(): Result<PopularMoviesApiResponse> =
                apiService.getPopularMovies(page = page)

            override suspend fun getResult(apiResponse: PopularMoviesApiResponse): List<MovieModel> =
                apiResponse.results
        }.execute()

    override suspend fun getCastOfMovie(movieId: Int): Result<List<MovieCastModel>> =
        object : NetworkResult<MovieCastApiResponse, List<MovieCastModel>>() {
            override suspend fun load(): Result<MovieCastApiResponse> =
                apiService.getCastOfAMovie(movieId = movieId)

            override suspend fun getResult(apiResponse: MovieCastApiResponse): List<MovieCastModel> =
                apiResponse.cast
        }.execute()

    override suspend fun saveMovies(movies: List<MovieModel>) =
        throw NotImplementedError()

    override suspend fun saveMovieCast(movieId: Int, movieCast: List<MovieCastModel>) =
        throw NotImplementedError()
}
