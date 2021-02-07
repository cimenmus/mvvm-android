package com.plumbers.mvvm.data.repository

import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.repository.person.PersonRepository
import com.plumbers.mvvm.data.repository.person.PersonRepositoryImpl
import com.plumbers.mvvm.data.source.person.PersonDataSource
import com.plumbers.mvvm.data.util.NetworkUtils
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.plumbers.mvvm.data.result.Result
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.succeeded
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

class PersonRepositoryTest {

    private lateinit var networkUtils: NetworkUtils
    private lateinit var personRemoteDataSource: PersonDataSource
    private lateinit var personLocalDataSource: PersonDataSource
    private lateinit var repository: PersonRepository

    @Before
    fun tearUp() {
        networkUtils = mockk(relaxed = true)
        personRemoteDataSource = mockk(relaxed = true)
        personLocalDataSource = mockk(relaxed = true)
        repository = spyk(
            PersonRepositoryImpl(networkUtils, personRemoteDataSource, personLocalDataSource),
            recordPrivateCalls = true
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `savePerson test`() {

        // given
        val person = PersonModel(
            id = 1234,
            name = "John Doe"
        )

        coEvery { personLocalDataSource.savePerson(person = person) } just runs

        // when
        runBlocking { repository.savePerson(person = person) }

        coVerify {
            repository.savePerson(person = person)
            personLocalDataSource.savePerson(person = person)
        }
        confirmVerified(
            repository,
            networkUtils,
            personRemoteDataSource,
            personLocalDataSource,
        )
    }

    @Test
    fun `getPersonDetails when network is available`() {

        // given
        val personId = 1234
        val person = PersonModel(
            id = personId,
            name = "John Doe"
        )

        every { networkUtils.isNetworkAvailable() } returns true

        val networkResult = Result.Success(person)
        coEvery { personRemoteDataSource.getPersonDetails(personId = personId) } returns networkResult

        coEvery { repository.savePerson(person) } just runs

        // when
        val result = runBlocking { repository.getPersonDetails(personId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        coVerify {
            repository.getPersonDetails(personId)
            networkUtils.isNetworkAvailable()
            personRemoteDataSource.getPersonDetails(personId = personId)
            repository.savePerson(person)
        }
        confirmVerified(
            repository,
            networkUtils,
            personRemoteDataSource,
            personLocalDataSource,
        )
    }

    @Test
    fun `getPersonDetails when network is not available`() {

        // given
        val personId = 1234
        val person = PersonModel(
            id = personId,
            name = "John Doe"
        )

        every { networkUtils.isNetworkAvailable() } returns false

        val databaseResult = Result.Success(person)
        coEvery { personLocalDataSource.getPersonDetails(personId = personId) } returns databaseResult

        // when
        val result = runBlocking { repository.getPersonDetails(personId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        coVerify {
            repository.getPersonDetails(personId)
            networkUtils.isNetworkAvailable()
            personLocalDataSource.getPersonDetails(personId = personId)
        }
        confirmVerified(
            repository,
            networkUtils,
            personRemoteDataSource,
            personLocalDataSource,
        )
    }
}
