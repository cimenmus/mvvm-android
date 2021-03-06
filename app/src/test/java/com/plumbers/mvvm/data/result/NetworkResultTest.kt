package com.plumbers.mvvm.data.result

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.api.response.PopularMoviesApiResponse
import com.plumbers.mvvm.data.model.MovieModel
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class NetworkResultTest {

    private lateinit var apiService: ApiService
    private lateinit var networkResult: NetworkResult<PopularMoviesApiResponse, List<MovieModel>>
    private val page = 4

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        networkResult = object : NetworkResult<PopularMoviesApiResponse, List<MovieModel>>() {
            override suspend fun load(): Result<PopularMoviesApiResponse> =
                apiService.getPopularMovies(page = page)

            override suspend fun getResult(apiResponse: PopularMoviesApiResponse): List<MovieModel> =
                apiResponse.results
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when api succeed`() {

        // given
        val movieList = listOf(
            MovieModel(id = 1231, title = "movie_1"),
            MovieModel(id = 1232, title = "movie_2"),
            MovieModel(id = 1233, title = "movie_3"),
            MovieModel(id = 1234, title = "movie_4")
        )
        val apiResponse = PopularMoviesApiResponse(page = page, results = movieList)
        val apiResult = Result.Success(apiResponse)
        coEvery { apiService.getPopularMovies(page) } returns apiResult

        // when
        val result = runBlocking { networkResult.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            networkResult.load()
            networkResult.getResult(apiResponse)
            apiService.getPopularMovies(page)
        }
        confirmVerified(apiService)
    }

    @Test
    fun `when api failed`() {

        // given
        val error = AppError(type = ErrorType.CONNECTION, message = "Network error")
        val apiResult = Result.Error(error)
        coEvery { apiService.getPopularMovies(page) } returns apiResult

        // when
        val result = runBlocking { networkResult.execute() }

        // then
        assertFalse(result.succeeded)
        assertEquals(error, result.error!!)

        coVerify {
            networkResult.load()
            apiService.getPopularMovies(page)
        }
        confirmVerified(
            apiService
        )
    }
}
