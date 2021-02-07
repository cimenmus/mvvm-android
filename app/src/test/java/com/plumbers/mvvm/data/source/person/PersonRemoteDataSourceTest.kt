package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.data.api.ApiService
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.succeeded
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class PersonRemoteDataSourceTest {

    private lateinit var apiService: ApiService
    private lateinit var dataSource: PersonDataSource

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        dataSource = spyk(PersonRemoteDataSource(apiService), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPersonDetails test`() {

        // given
        val personId = 1234

        val person = PersonModel(id = personId, name = "John Doe")
        val apiResult = Result.Success(person)
        coEvery { apiService.getPersonDetails(personId) } returns apiResult

        // when
        val result = runBlocking { dataSource.getPersonDetails(personId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(apiResult, result)

        coVerify {
            dataSource.getPersonDetails(personId)
            apiService.getPersonDetails(personId)
        }
        confirmVerified(dataSource, apiService)
    }

    @Test
    fun `savePerson test`() {

        // given
        val person = PersonModel(id = 1234, name = "John Doe")

        // when
        lateinit var error: NotImplementedError
        try {
            runBlocking { dataSource.savePerson(person) }
        } catch (e: NotImplementedError) {
            error = e
        }

        // then
        assertNotNull(error)

        coVerify {
            dataSource.savePerson(person)
        }
        confirmVerified(dataSource, apiService)
    }
}
