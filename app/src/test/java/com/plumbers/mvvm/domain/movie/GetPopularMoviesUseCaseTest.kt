package com.plumbers.mvvm.domain.movie

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.error
import com.plumbers.mvvm.data.result.succeeded
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        ioDispatcher = Dispatchers.IO
        useCase = spyk(
            GetPopularMoviesUseCase(movieRepository, ioDispatcher),
            recordPrivateCalls = true
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when repository returned succeed`() {

        // given
        val page = 2
        val parameter = PopularMoviesParameter(page = page)

        val movie1 = MovieModel(
            id = 1231,
            title = "title_1",
            average = 85.61,
            backdropImagePath = "/uheng88231.jpg"
        )

        val movie2 = MovieModel(
            id = 1232,
            title = "title_2",
            average = 85.62,
            backdropImagePath = "/uheng88232.jpg"
        )

        val movie3 = MovieModel(
            id = 1233,
            title = "title_3",
            average = 85.63,
            backdropImagePath = "/uheng88233.jpg"
        )
        val movie4 = MovieModel(
            id = 1234,
            title = "title_4",
            average = 85.64,
            backdropImagePath = "/uheng88234.jpg"
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
        )

        val repoResponse = Result.Success(movieList)
        coEvery { movieRepository.getPopularMovies(page) } returns repoResponse

        // when
        val response = runBlocking { useCase(parameters = parameter) }

        // then
        assertTrue(response is Result.Success)
        assertTrue(response.succeeded)
        assertEquals(movieList, response.data)

        coVerify {
            useCase(parameter)
            useCase.invoke(parameter)
            useCase.invoke(any())
            useCase.execute(parameters = parameter)
            movieRepository.getPopularMovies(page = page)
        }
        confirmVerified(useCase, movieRepository)
    }

    @Test
    fun `when repository returned error`() {

        // given
        val page = 2
        val parameter = PopularMoviesParameter(page = page)

        val error = AppError(type = ErrorType.USECASE, message = "sample_error_message")
        val repoResponse = Result.Error(appError = error)
        coEvery { movieRepository.getPopularMovies(page) } returns repoResponse

        // when
        val response = runBlocking { useCase(parameters = parameter) }

        // then
        assertTrue(response is Result.Error)
        assertFalse(response.succeeded)
        assertEquals(error, response.error!!)

        coVerify {
            useCase(parameter)
            useCase.invoke(parameter)
            useCase.invoke(any())
            useCase.execute(parameters = parameter)
            movieRepository.getPopularMovies(page = page)
        }
        confirmVerified(useCase, movieRepository)
    }
}
