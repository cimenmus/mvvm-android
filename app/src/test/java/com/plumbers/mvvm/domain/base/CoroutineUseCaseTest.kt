package com.plumbers.mvvm.domain.base

import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.error
import com.plumbers.mvvm.data.result.succeeded
import com.plumbers.mvvm.domain.person.PersonDetailParameter
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class CoroutineUseCaseTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var testPerson: PersonModel
    private lateinit var coroutineDispatcher: CoroutineDispatcher
    private lateinit var useCase: CoroutineUseCase<PersonDetailParameter, PersonModel>

    @Before
    fun setUp() {
        personRepository = mockk(relaxed = true)
        testPerson = PersonModel(
            id = 1234,
            name = "John Done"
        )
        coroutineDispatcher = Dispatchers.IO
        useCase =
            object : CoroutineUseCase<PersonDetailParameter, PersonModel>(coroutineDispatcher) {
                override suspend fun execute(parameters: PersonDetailParameter): PersonModel {
                    personRepository.getPersonDetails(parameters.personId)
                    return testPerson
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

        coEvery { personRepository.getPersonDetails(personId = personId) } returns mockk()

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
}
