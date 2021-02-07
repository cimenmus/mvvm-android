package com.plumbers.mvvm.data.repository

import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.data.repository.movie.MovieRepositoryImpl
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.succeeded
import com.plumbers.mvvm.data.source.movie.MovieDataSource
import com.plumbers.mvvm.data.util.NetworkUtils
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    private lateinit var networkUtils: NetworkUtils
    private lateinit var movieRemoteDataSource: MovieDataSource
    private lateinit var movieLocalDataSource: MovieDataSource
    private lateinit var repository: MovieRepository

    @Before
    fun tearUp() {
        networkUtils = mockk(relaxed = true)
        movieRemoteDataSource = mockk(relaxed = true)
        movieLocalDataSource = mockk(relaxed = true)
        repository = spyk(
            MovieRepositoryImpl(networkUtils, movieRemoteDataSource, movieLocalDataSource),
            recordPrivateCalls = true
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `saveMovies test`() {

        // given
        val movie1 = MovieModel(
            id = 1231,
            title = "Batman"
        )
        val movie2 = MovieModel(
            id = 1232,
            title = "Joker"
        )
        val movie3 = MovieModel(
            id = 1233,
            title = "Spider-man"
        )
        val movie4 = MovieModel(
            id = 1234,
            title = "Superman"
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
        )

        coEvery { movieLocalDataSource.saveMovies(movies = movieList) } just runs

        // when
        runBlocking { repository.saveMovies(movies = movieList) }

        coVerify {
            repository.saveMovies(movies = movieList)
            movieLocalDataSource.saveMovies(movies = movieList)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }

    @Test
    fun `getPopularMovies when network is available`() {

        // given
        val page = 3
        val movie1 = MovieModel(
            id = 1231,
            title = "Batman"
        )
        val movie2 = MovieModel(
            id = 1232,
            title = "Joker"
        )
        val movie3 = MovieModel(
            id = 1233,
            title = "Spider-man"
        )
        val movie4 = MovieModel(
            id = 1234,
            title = "Superman"
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
        )

        every { networkUtils.isNetworkAvailable() } returns true

        val networkResult = Result.Success(movieList)
        coEvery { movieRemoteDataSource.getPopularMovies(page = page) } returns networkResult

        coEvery { repository.saveMovies(movieList) } just runs

        // when
        val result = runBlocking { repository.getPopularMovies(page) }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            repository.getPopularMovies(page)
            networkUtils.isNetworkAvailable()
            movieRemoteDataSource.getPopularMovies(page = page)
            repository.saveMovies(movieList)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }

    @Test
    fun `getPopularMovies when network is not available`() {

        // given
        val page = 3
        val movie1 = MovieModel(
            id = 1231,
            title = "Batman"
        )
        val movie2 = MovieModel(
            id = 1232,
            title = "Joker"
        )
        val movie3 = MovieModel(
            id = 1233,
            title = "Spider-man"
        )
        val movie4 = MovieModel(
            id = 1234,
            title = "Superman"
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
        )

        every { networkUtils.isNetworkAvailable() } returns false

        val databaseResult = Result.Success(movieList)
        coEvery { movieLocalDataSource.getPopularMovies(page = page) } returns databaseResult

        // when
        val result = runBlocking { repository.getPopularMovies(page) }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            repository.getPopularMovies(page)
            networkUtils.isNetworkAvailable()
            movieLocalDataSource.getPopularMovies(page = page)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }

    @Test
    fun `saveMovieCast test`() {

        // given
        val movieId = 9876
        val cast1 = MovieCastModel(
            id = 1231,
            movieId = movieId,
            name = "name_1"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            movieId = movieId,
            name = "name_2"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            movieId = movieId,
            name = "name_3"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            movieId = movieId,
            name = "name_4"
        )
        val castList = listOf(
            cast1,
            cast2,
            cast3,
            cast4
        )

        coEvery {
            movieLocalDataSource.saveMovieCast(
                movieId = movieId,
                movieCast = castList
            )
        } just runs

        // when
        runBlocking { repository.saveMovieCast(movieId = movieId, movieCast = castList) }

        coVerify {
            repository.saveMovieCast(movieId = movieId, movieCast = castList)
            movieLocalDataSource.saveMovieCast(movieId = movieId, movieCast = castList)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }

    @Test
    fun `getCastOfMovie when network is available`() {

        // given
        val movieId = 9876
        val cast1 = MovieCastModel(
            id = 1231,
            movieId = movieId,
            name = "name_1"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            movieId = movieId,
            name = "name_2"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            movieId = movieId,
            name = "name_3"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            movieId = movieId,
            name = "name_4"
        )
        val castList = listOf(
            cast1,
            cast2,
            cast3,
            cast4
        )

        every { networkUtils.isNetworkAvailable() } returns true

        val networkResult = Result.Success(castList)
        coEvery { movieRemoteDataSource.getCastOfMovie(movieId = movieId) } returns networkResult

        coEvery { repository.saveMovieCast(movieId, castList) } just runs

        // when
        val result = runBlocking { repository.getCastOfMovie(movieId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(castList, result.data!!)

        coVerify {
            repository.getCastOfMovie(movieId)
            networkUtils.isNetworkAvailable()
            movieRemoteDataSource.getCastOfMovie(movieId = movieId)
            repository.saveMovieCast(movieId, castList)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }

    @Test
    fun `getCastOfMovie when network is not available`() {

        // given
        val movieId = 9876
        val cast1 = MovieCastModel(
            id = 1231,
            movieId = movieId,
            name = "name_1"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            movieId = movieId,
            name = "name_2"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            movieId = movieId,
            name = "name_3"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            movieId = movieId,
            name = "name_4"
        )
        val castList = listOf(
            cast1,
            cast2,
            cast3,
            cast4
        )

        every { networkUtils.isNetworkAvailable() } returns false

        val databaseResult = Result.Success(castList)
        coEvery { movieLocalDataSource.getCastOfMovie(movieId = movieId) } returns databaseResult

        // when
        val result = runBlocking { repository.getCastOfMovie(movieId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(castList, result.data!!)

        coVerify {
            repository.getCastOfMovie(movieId)
            networkUtils.isNetworkAvailable()
            movieLocalDataSource.getCastOfMovie(movieId = movieId)
        }
        confirmVerified(
            repository,
            networkUtils,
            movieRemoteDataSource,
            movieLocalDataSource,
        )
    }
}
