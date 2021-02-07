package com.plumbers.mvvm.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plumbers.mvvm.data.db.AppDatabase
import com.plumbers.mvvm.data.db.PersonDao
import com.plumbers.mvvm.data.model.PersonModel
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonDaoAndroidTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: PersonDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = appDatabase.personDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeAndRead() {

        // given
        val samplePerson = PersonModel(
            id = 1234,
            name = "John Doe",
            biography = "bio",
            imagePath = "/jhb73hbf.jpg"
        )

        // when
        runBlocking { dao.add(person = samplePerson) }
        val readResult1 = runBlocking { dao.getPerson(personId = samplePerson.id) }
        val readResult2 = runBlocking { dao.getPerson(personId = 5678) }

        // then
        assertNotNull(readResult1)
        assertEquals(samplePerson, readResult1)
        assertNull(readResult2)
    }

    @Test
    fun delete() {

        // given
        val samplePerson1 = PersonModel(
            id = 1234,
            name = "John Doe",
            biography = "bio",
            imagePath = "/jhb73hbf.jpg"
        )
        val samplePerson2 = PersonModel(
            id = 1235,
            name = "Jane Doe",
            biography = "bio_jane",
            imagePath = "/883jmfk.jpg"
        )

        // when
        runBlocking { dao.add(person = samplePerson1) }
        runBlocking { dao.add(person = samplePerson2) }

        val readResultBeforeDelete1 = runBlocking { dao.getPerson(personId = samplePerson1.id) }
        val readResultBeforeDelete2 = runBlocking { dao.getPerson(personId = samplePerson2.id) }

        runBlocking { dao.delete(person = samplePerson1) }

        val readResultAfterDelete1 = runBlocking { dao.getPerson(personId = samplePerson1.id) }
        val readResultAfterDelete2 = runBlocking { dao.getPerson(personId = samplePerson2.id) }

        // then
        assertEquals(samplePerson1, readResultBeforeDelete1)
        assertEquals(samplePerson2, readResultBeforeDelete2)
        assertNull(readResultAfterDelete1)
        assertEquals(samplePerson2, readResultAfterDelete2)
    }
}
