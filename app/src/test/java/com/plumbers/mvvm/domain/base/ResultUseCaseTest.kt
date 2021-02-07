package com.plumbers.mvvm.domain.base

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.error
import com.plumbers.mvvm.data.result.succeeded
import com.plumbers.mvvm.domain.person.PersonDetailParameter
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class ResultUseCaseTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var coroutineDispatcher: CoroutineDispatcher
    private lateinit var useCase: ResultUseCase<PersonDetailParameter, PersonModel>

    @Before
    fun setUp() {
        personRepository = mockk(relaxed = true)
        coroutineDispatcher = Dispatchers.IO
        useCase =
            object : ResultUseCase<PersonDetailParameter, PersonModel>(coroutineDispatcher) {
                override suspend fun execute(parameters: PersonDetailParameter): Result<PersonModel> {
                    return personRepository.getPersonDetails(parameters.personId)
                }
            }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when an exception is thrown`() {

        // given
        val personId = 1234
        val params = PersonDetailParameter(personId = personId)

        val exception = Exception("sample_exception")
        coEvery { personRepository.getPersonDetails(personId = personId) } throws exception

        // when
        val result = runBlocking { useCase(parameters = params) }

        // then
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.USECASE, result.error!!.type)
        assertEquals(0, result.error!!.code)
        assertEquals("sample_exception", result.error!!.message)

        coVerify {
            useCase(parameters = params)
            useCase.invoke(parameters = params)
            useCase.execute(parameters = params)
        }
        confirmVerified(personRepository)
    }

    @Test
    fun `when succeed`() {

        // given
        val personId = 1234
        val params = PersonDetailParameter(personId = personId)

        val testPerson = PersonModel(
            id = 1234,
            name = "John Done"
        )
        val repositoryResult = Result.Success(testPerson)
        coEvery { personRepository.getPersonDetails(personId = personId) } returns repositoryResult

        // when
        val result = runBlocking { useCase(parameters = params) }

        // then
        assertTrue(result.succeeded)
        assertEquals(testPerson, result.data!!)

        coVerify {
            useCase(parameters = params)
            useCase.invoke(parameters = params)
            useCase.execute(parameters = params)
        }
        confirmVerified(personRepository)
    }

    @Test
    fun `when repository returns error`() {

        // given
        val personId = 1234
        val params = PersonDetailParameter(personId = personId)

        val error = AppError(type = ErrorType.CONNECTION, message = "network error")
        val repositoryResult = Result.Error(error)
        coEvery { personRepository.getPersonDetails(personId = personId) } returns repositoryResult

        // when
        val result = runBlocking { useCase(parameters = params) }

        // then
        assertFalse(result.succeeded)
        assertTrue(result is Result.Error)
        assertEquals(error, result.error!!)

        coVerify {
            useCase(parameters = params)
            useCase.invoke(parameters = params)
            useCase.execute(parameters = params)
        }
        confirmVerified(personRepository)
    }
}
