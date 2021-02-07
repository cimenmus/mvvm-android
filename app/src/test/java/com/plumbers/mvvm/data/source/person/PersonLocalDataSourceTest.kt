package com.plumbers.mvvm.data.source.person

import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.db.PersonDao
import com.plumbers.mvvm.data.model.PersonModel
import com.plumbers.mvvm.data.result.data
import com.plumbers.mvvm.data.result.error
import com.plumbers.mvvm.data.result.succeeded
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class PersonLocalDataSourceTest {

    private lateinit var personDao: PersonDao
    private lateinit var dataSource: PersonDataSource

    @Before
    fun setUp() {
        personDao = mockk(relaxed = true)
        dataSource = spyk(PersonLocalDataSource(personDao), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPersonDetails when succeed`() {

        // given
        val personId = 1234
        val person = PersonModel(id = personId, name = "John Doe")
        coEvery { personDao.getPerson(personId = personId) } returns person

        // when
        val result = runBlocking { dataSource.getPersonDetails(personId = personId) }

        // then
        assertTrue(result.succeeded)
        assertEquals(person, result.data!!)

        /*
        coEvery {
            dataSource.getPersonDetails(personId = personId)
            personDao.getPerson(personId = personId)
        }
        confirmVerified(dataSource, personDao)
        */
    }

    @Test
    fun `getPersonDetails when failed`() {

        // given
        val personId = 1234
        coEvery { personDao.getPerson(personId = personId) } returns null

        // when
        val result = runBlocking { dataSource.getPersonDetails(personId = personId) }

        // then
        assertFalse(result.succeeded)
        assertEquals(ErrorType.DB_ITEM_NOT_FOUND, result.error!!.type)
        assertEquals("Can not found item on database", result.error!!.message)

        /*
        coEvery {
            dataSource.getPersonDetails(personId = personId)
            personDao.getPerson(personId = personId)
        }
        confirmVerified(dataSource, personDao)
        */
    }

    @Test
    fun `savePerson test`() {

        // given
        val person = PersonModel(id = 1234, name = "John Doe")
        coEvery { personDao.add(person = person) } just runs

        // when
        runBlocking { dataSource.savePerson(person) }

        /*
        // then
        coEvery {
            dataSource.savePerson(person = person)
            personDao.add(person = person)
        }
        confirmVerified(dataSource, personDao)
        */
    }
}
