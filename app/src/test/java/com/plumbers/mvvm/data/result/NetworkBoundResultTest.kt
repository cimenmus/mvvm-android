package com.plumbers.mvvm.data.result

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.source.person.PersonDataSource
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class NetworkBoundResultTest {

    private lateinit var personRemoteDataSource: PersonDataSource
    private lateinit var personLocalDataSource: PersonDataSource
    private val personId = 1234

    @Before
    fun setUp() {
        personRemoteDataSource = mockk(relaxed = true)
        personLocalDataSource = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should not fetch from network when no need to fetch`() {

        // given
        coEvery { personRemoteDataSource.getPersonDetails(personId) } returns mockk()

        val person = PersonModel(
            id = 1234,
            name = "John Doe"
        )
        val personLocalResponse = Result.Success(person)
        coEvery { personLocalDataSource.getPersonDetails(personId = personId) } returns personLocalResponse

        val resource = object : NetworkBoundResult<PersonModel>() {

            override fun shouldFetch(): Boolean = false

            override fun isOnline(): Boolean = true

            override suspend fun loadFromNetwork(): Result<PersonModel> =
                personRemoteDataSource.getPersonDetails(personId)

            override suspend fun loadFromDb(): Result<PersonModel> =
                personLocalDataSource.getPersonDetails(personId = personId)

            override suspend fun saveNetworkResult(data: PersonModel) =
                personLocalDataSource.savePerson(person = data)
        }
        // when
        val result = runBlocking { resource.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        coVerify {
            resource.execute()
            resource.isOnline()
            resource.shouldFetch()
            resource.loadFromDb()
            personLocalDataSource.getPersonDetails(personId = personId)
        }
        confirmVerified(
            personRemoteDataSource,
            personLocalDataSource,
        )
    }

    @Test
    fun `should not fetch from network when need to fetch from network but network is not available`() {

        // given
        coEvery { personRemoteDataSource.getPersonDetails(personId) } returns mockk()

        val person = PersonModel(
            id = 1234,
            name = "John Doe"
        )
        val personLocalResponse = Result.Success(person)
        coEvery { personLocalDataSource.getPersonDetails(personId = personId) } returns personLocalResponse

        val resource = object : NetworkBoundResult<PersonModel>() {

            override fun shouldFetch(): Boolean = true

            override fun isOnline(): Boolean = false

            override suspend fun loadFromNetwork(): Result<PersonModel> =
                personRemoteDataSource.getPersonDetails(personId)

            override suspend fun loadFromDb(): Result<PersonModel> =
                personLocalDataSource.getPersonDetails(personId = personId)

            override suspend fun saveNetworkResult(data: PersonModel) =
                personLocalDataSource.savePerson(person = data)
        }
        // when
        val result = runBlocking { resource.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        coVerify {
            resource.execute()
            resource.isOnline()
            resource.shouldFetch()
            resource.loadFromDb()
            personLocalDataSource.getPersonDetails(personId = personId)
        }
        confirmVerified(
            personRemoteDataSource,
            personLocalDataSource,
        )
    }

    @Test
    fun `should fetch from network and save to database when succeed`() {

        // given
        coEvery { personRemoteDataSource.getPersonDetails(personId) } returns mockk()

        val person = PersonModel(
            id = 1234,
            name = "John Doe"
        )
        val personRemoteResponse = Result.Success(person)
        coEvery { personRemoteDataSource.getPersonDetails(personId = personId) } returns personRemoteResponse

        coEvery { personLocalDataSource.savePerson(person = person) } just runs

        val resource = object : NetworkBoundResult<PersonModel>() {

            override fun shouldFetch(): Boolean = true

            override fun isOnline(): Boolean = true

            override suspend fun loadFromNetwork(): Result<PersonModel> =
                personRemoteDataSource.getPersonDetails(personId)

            override suspend fun loadFromDb(): Result<PersonModel> =
                personLocalDataSource.getPersonDetails(personId = personId)

            override suspend fun saveNetworkResult(data: PersonModel) =
                personLocalDataSource.savePerson(person = data)
        }
        // when
        val result = runBlocking { resource.execute() }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        coVerify {
            resource.execute()
            resource.isOnline()
            resource.shouldFetch()
            resource.loadFromNetwork()
            resource.saveNetworkResult(data = person)
            personRemoteDataSource.getPersonDetails(personId = personId)
            personLocalDataSource.savePerson(person = person)
        }
        confirmVerified(
            personRemoteDataSource,
            personLocalDataSource,
        )
    }

    @Test
    fun `should fetch from network and do not save to database when it is not succeed`() {

        // given
        coEvery { personRemoteDataSource.getPersonDetails(personId) } returns mockk()

        val person = PersonModel(
            id = 1234,
            name = "John Doe"
        )
        val error = AppError(type = ErrorType.CONNECTION, message = "network error")
        val personRemoteResponse = Result.Error(error)
        coEvery { personRemoteDataSource.getPersonDetails(personId = personId) } returns personRemoteResponse

        coEvery { personLocalDataSource.savePerson(person = person) } just runs

        val resource = object : NetworkBoundResult<PersonModel>() {

            override fun shouldFetch(): Boolean = true

            override fun isOnline(): Boolean = true

            override suspend fun loadFromNetwork(): Result<PersonModel> =
                personRemoteDataSource.getPersonDetails(personId)

            override suspend fun loadFromDb(): Result<PersonModel> =
                personLocalDataSource.getPersonDetails(personId = personId)

            override suspend fun saveNetworkResult(data: PersonModel) =
                personLocalDataSource.savePerson(person = data)
        }
        // when
        val result = runBlocking { resource.execute() }

        // then
        assertFalse(result.succeeded)
        assertEquals(error, result.error!!)

        coVerify {
            resource.execute()
            resource.isOnline()
            resource.shouldFetch()
            resource.loadFromNetwork()
            personRemoteDataSource.getPersonDetails(personId = personId)
        }
        confirmVerified(
            personRemoteDataSource,
            personLocalDataSource,
        )
    }
}
