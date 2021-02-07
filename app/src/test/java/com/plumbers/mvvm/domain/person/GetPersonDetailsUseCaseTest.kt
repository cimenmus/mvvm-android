package com.plumbers.mvvm.domain.person

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
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

class GetPersonDetailsUseCaseTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var useCase: GetPersonDetailsUseCase

    @Before
    fun setUp() {
        personRepository = mockk(relaxed = true)
        ioDispatcher = Dispatchers.IO
        useCase = spyk(
            GetPersonDetailsUseCase(personRepository, ioDispatcher),
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
        val personId = 1234
        val parameter = PersonDetailParameter(personId = personId)

        val personModel = PersonModel(
            id = 1234,
            name = "John Doe",
            biography = "test_biography",
            imagePath = "/uheng88233.jpg"
        )

        val repoResponse = Result.Success(personModel)
        coEvery { personRepository.getPersonDetails(personId) } returns repoResponse

        // when
        val response = runBlocking { useCase(parameters = parameter) }

        // then
        assertTrue(response is Result.Success)
        assertTrue(response.succeeded)
        assertEquals(personModel, response.data)

        coVerify {
            useCase(parameter)
            useCase.invoke(parameter)
            useCase.invoke(any())
            useCase.execute(parameters = parameter)
            personRepository.getPersonDetails(personId = personId)
        }
        confirmVerified(useCase, personRepository)
    }

    @Test
    fun `when repository returned error`() {

        // given
        val personId = 1234
        val parameter = PersonDetailParameter(personId = personId)

        val error = AppError(type = ErrorType.USECASE, message = "sample_error_message")
        val repoResponse = Result.Error(appError = error)
        coEvery { personRepository.getPersonDetails(personId) } returns repoResponse

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
            personRepository.getPersonDetails(personId = personId)
        }
        confirmVerified(useCase, personRepository)
    }
}
