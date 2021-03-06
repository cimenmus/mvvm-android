package com.plumbers.mvvm.data.result

import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.model.PersonModel
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabaseResultTest {

    private lateinit var movieDao: MovieDao
    private val offset = 40

    @Before
    fun setUp() {
        movieDao = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when result is list and it is empty`() {

        // given
        val movieList = listOf(
            MovieModel(id = 1231, title = "movie_1"),
            MovieModel(id = 1232, title = "movie_2"),
            MovieModel(id = 1233, title = "movie_3"),
            MovieModel(id = 1234, title = "movie_4")
        )

        coEvery { movieDao.getPopularMovies(offset) } returns movieList

        val databaseResult = object : DatabaseResult<List<MovieModel>>() {
            override suspend fun load(): List<MovieModel> =
                movieDao.getPopularMovies(offset)
        }

        // when
        val result = runBlocking { databaseResult.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(movieList, result.data!!)

        coVerify {
            databaseResult.load()
            movieDao.getPopularMovies(offset)
        }
        confirmVerified(movieDao)
    }

    @Test
    fun `when result is list and has items`() {

        // given
        val movieList = listOf<MovieModel>()

        coEvery { movieDao.getPopularMovies(offset) } returns movieList

        val databaseResult = object : DatabaseResult<List<MovieModel>>() {
            override suspend fun load(): List<MovieModel> =
                movieDao.getPopularMovies(offset)
        }

        // when
        val result = runBlocking { databaseResult.execute() }

        // then
        assertFalse(result.succeeded)
        assertEquals(ErrorType.DB_ITEM_NOT_FOUND, result.error!!.type)
        assertEquals("Can not found item on database", result.error!!.message)

        coVerify {
            databaseResult.load()
            movieDao.getPopularMovies(offset)
        }
        confirmVerified(movieDao)
    }

    @Test
    fun `when result is null`() {

        // given
        val movieList: List<MovieModel>? = null

        val databaseResult = object : DatabaseResult<List<MovieModel>>() {
            override suspend fun load(): List<MovieModel>? = movieList
        }

        // when
        val result = runBlocking { databaseResult.execute() }

        // then
        assertFalse(result.succeeded)
        assertEquals(ErrorType.DB_ITEM_NOT_FOUND, result.error!!.type)
        assertEquals("Can not found item on database", result.error!!.message)
    }

    @Test
    fun `when result is object`() {

        // given
        val person = PersonModel(id = 1234, name = "John Doe")

        val databaseResult = object : DatabaseResult<PersonModel>() {
            override suspend fun load(): PersonModel = person
        }

        // when
        val result = runBlocking { databaseResult.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)
    }
}
