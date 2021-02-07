package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.api.response.MovieCastApiResponse
import com.plumbers.mvvm.data.api.response.PopularMoviesApiResponse
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.succeeded
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieRemoteDataSourceTest {

    private lateinit var apiService: ApiService
    private lateinit var dataSource: MovieDataSource

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        dataSource = spyk(MovieRemoteDataSource(apiService), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPopularMovies test`() {

        // given
        val page = 3
        val movieList = listOf(
            MovieModel(id = 1, title = "movie_1"),
            MovieModel(id = 2, title = "movie_2"),
            MovieModel(id = 3, title = "movie_3"),
            MovieModel(id = 4, title = "movie_4")
        )

        val apiResponse = PopularMoviesApiResponse(page = page, results = movieList)
        val apiResult = Result.Success(apiResponse)
        coEvery { apiService.getPopularMovies(page = page) } returns apiResult

        // when
        val result = runBlocking { dataSource.getPopularMovies(page = page) }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            dataSource.getPopularMovies(page = page)
            apiService.getPopularMovies(page = page)
        }
        confirmVerified(dataSource, apiService)
    }

    @Test
    fun `saveMovies test`() {

        // given
        val movieList = listOf<MovieModel>()

        // when
        lateinit var error: NotImplementedError
        try {
            runBlocking { dataSource.saveMovies(movieList) }
        } catch (e: NotImplementedError) {
            error = e
        }

        // then
        assertNotNull(error)

        coVerify {
            dataSource.saveMovies(movieList)
        }
        confirmVerified(dataSource, apiService)
    }

    @Test
    fun `getCastOfMovie test`() {

        // given
        val movieId = 1234
        val castList = listOf(
            MovieCastModel(id = 1, name = "name_1"),
            MovieCastModel(id = 2, name = "name_2"),
            MovieCastModel(id = 3, name = "name_3"),
            MovieCastModel(id = 4, name = "name_4"),
        )

        val apiResponse = MovieCastApiResponse(id = movieId, cast = castList)
        val apiResult = Result.Success(apiResponse)
        coEvery { apiService.getCastOfAMovie(movieId = movieId) } returns apiResult

        // when
        val result = runBlocking { dataSource.getCastOfMovie(movieId = movieId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(castList, result.data!!)

        coVerify {
            apiService.getCastOfAMovie(movieId = movieId)
            dataSource.getCastOfMovie(movieId = movieId)
        }
        confirmVerified(dataSource, apiService)
    }

    @Test
    fun `saveMovieCast test`() {

        // given
        val movieId = 1234
        val movieCastList = listOf<MovieCastModel>()

        // when
        lateinit var error: NotImplementedError
        try {
            runBlocking { dataSource.saveMovieCast(movieId, movieCastList) }
        } catch (e: NotImplementedError) {
            error = e
        }

        // then
        assertNotNull(error)

        coVerify {
            dataSource.saveMovieCast(movieId, movieCastList)
        }
        confirmVerified(dataSource, apiService)
    }
}
