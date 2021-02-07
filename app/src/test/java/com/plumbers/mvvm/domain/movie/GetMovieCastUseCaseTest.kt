package com.plumbers.mvvm.domain.movie

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.error
import com.plumbers.mvvm.data.result.succeeded
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GetMovieCastUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var useCase: GetMovieCastUseCase

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        ioDispatcher = Dispatchers.IO
        useCase = spyk(
            GetMovieCastUseCase(movieRepository, ioDispatcher),
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
        val movieId = 1234
        val parameter = MovieCastParameter(movieId = movieId)

        val cast1 = MovieCastModel(
            id = 1231,
            name = "name_1",
            character = "character_1",
            imagePath = "/uheng88231.jpg"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            name = "name_2",
            character = "character_2",
            imagePath = "/uheng88232.jpg"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            name = "name_3",
            character = "character_3",
            imagePath = "/uheng88233.jpg"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            name = "name_4",
            character = "character_4",
            imagePath = "/uheng88234.jpg"
        )

        val cast = listOf(
            cast1,
            cast2,
            cast3,
            cast4
        )

        val repoResponse = Result.Success(cast)
        coEvery { movieRepository.getCastOfMovie(movieId) } returns repoResponse

        // when
        val response = runBlocking { useCase(parameters = parameter) }

        // then
        assertTrue(response is Result.Success)
        assertTrue(response.succeeded)
        assertEquals(cast, response.data)

        coVerify {
            useCase(parameter)
            useCase.invoke(parameter)
            useCase.invoke(any())
            useCase.execute(parameters = parameter)
            movieRepository.getCastOfMovie(movieId = movieId)
        }
        confirmVerified(useCase, movieRepository)
    }

    @Test
    fun `when repository returned error`() {

        // given
        val movieId = 1234
        val parameter = MovieCastParameter(movieId = movieId)

        val error = AppError(type = ErrorType.USECASE, message = "sample_error_message")
        val repoResponse = Result.Error(appError = error)
        coEvery { movieRepository.getCastOfMovie(movieId) } returns repoResponse

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
            movieRepository.getCastOfMovie(movieId = movieId)
        }
        confirmVerified(useCase, movieRepository)
    }
}