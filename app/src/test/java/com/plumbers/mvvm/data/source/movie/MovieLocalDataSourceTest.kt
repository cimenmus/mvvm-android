package com.plumbers.mvvm.data.source.movie

import com.plumbers.mvvm.data.db.MovieCastDao
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.succeeded
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieLocalDataSourceTest {

    private lateinit var movieDao: MovieDao
    private lateinit var movieCastDao: MovieCastDao
    private lateinit var dataSource: MovieDataSource

    @Before
    fun setUp() {
        movieDao = mockk(relaxed = true)
        movieCastDao = mockk(relaxed = true)
        dataSource = spyk(MovieLocalDataSource(movieDao, movieCastDao), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPopularMovies test`() {

        // given
        val page = 3
        val offset = 40
        val movieList = listOf(
            MovieModel(id = 1, title = "movie_1"),
            MovieModel(id = 2, title = "movie_2"),
            MovieModel(id = 3, title = "movie_3"),
            MovieModel(id = 4, title = "movie_4")
        )

        coEvery { movieDao.getPopularMovies(offset) } returns movieList

        // when
        val result = runBlocking { dataSource.getPopularMovies(page = page) }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            dataSource.getPopularMovies(page = page)
            movieDao.getPopularMovies(offset)
        }
        confirmVerified(dataSource, movieDao, movieCastDao)
    }

    @Test
    fun `saveMovies test`() {

        // given
        val movieList = listOf(
            MovieModel(id = 1, title = "movie_1"),
            MovieModel(id = 2, title = "movie_2"),
            MovieModel(id = 3, title = "movie_3"),
            MovieModel(id = 4, title = "movie_4")
        )

        coEvery { movieDao.add(movieList) } just runs

        // when
        runBlocking { dataSource.saveMovies(movies = movieList) }

        // then
        coVerify {
            dataSource.saveMovies(movies = movieList)
            movieDao.add(movieList)
        }
        confirmVerified(dataSource, movieDao, movieCastDao)
    }

    @Test
    fun `getCastOfMovie test`() {

        // given
        val movieId = 1234
        val movieCastList = listOf(
            MovieCastModel(id = 1, name = "name_1"),
            MovieCastModel(id = 2, name = "name_2"),
            MovieCastModel(id = 3, name = "name_3"),
            MovieCastModel(id = 4, name = "name_4"),
        )

        coEvery { movieCastDao.getCastOfMovie(movieId = movieId) } returns movieCastList

        // when
        val result = runBlocking { dataSource.getCastOfMovie(movieId = movieId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieCastList, result.data!!)

        coVerify {
            dataSource.getCastOfMovie(movieId = movieId)
            movieCastDao.getCastOfMovie(movieId = movieId)
        }
        confirmVerified(dataSource, movieDao, movieCastDao)
    }

    @Test
    fun `saveMovieCast test`() {

        // given
        val movieId = 1234

        val cast1 = MovieCastModel(id = 1, name = "name_1")
        val cast2 = MovieCastModel(id = 2, name = "name_2")
        val movieCastList = listOf(
            cast1,
            cast2,
        )

        coEvery { movieCastDao.add(cast = any()) } just runs

        // when
        runBlocking { dataSource.saveMovieCast(movieId = movieId, movieCast = movieCastList) }

        // then
        coVerify {
            dataSource.saveMovieCast(movieId = movieId, movieCast = movieCastList)
            movieCastDao.add(
                withArg {
                    assertEquals(cast1.id, it[0].id)
                    assertEquals(cast1.name, it[0].name)
                    assertEquals(movieId, it[0].movieId)

                    assertEquals(cast2.id, it[1].id)
                    assertEquals(cast2.name, it[1].name)
                    assertEquals(movieId, it[1].movieId)
                }
            )
        }
        confirmVerified(dataSource, movieDao, movieCastDao)
    }
}
