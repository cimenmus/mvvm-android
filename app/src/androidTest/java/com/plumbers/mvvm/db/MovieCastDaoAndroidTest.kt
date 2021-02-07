package com.plumbers.mvvm.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plumbers.mvvm.data.db.AppDatabase
import com.plumbers.mvvm.data.db.MovieCastDao
import com.plumbers.mvvm.data.model.MovieCastModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieCastDaoAndroidTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: MovieCastDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = appDatabase.movieCastDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeAndRead() {

        // given
        val cast1 = MovieCastModel(
            id = 1231,
            name = "name_1",
            character = "character_1",
            order = 5,
            movieId = 9876,
            imagePath = "/7nhbhjh19.jpg"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            name = "name_2",
            character = "character_2",
            order = 3,
            movieId = 9876,
            imagePath = "/7nhbhjh12.jpg"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            name = "name_3",
            character = "character_3",
            order = 6,
            movieId = 9876,
            imagePath = "/7nhbhjh18.jpg"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            name = "name_4",
            character = "character_4",
            order = 2,
            movieId = 9876,
            imagePath = "/7nhbhjh19.jpg"
        )
        val cast5 = MovieCastModel(
            id = 1235,
            name = "name_5",
            character = "character_5",
            order = 1,
            movieId = 9870,
            imagePath = "/7nhbhjh10.jpg"
        )
        val cast6 = MovieCastModel(
            id = 1236,
            name = "name_6",
            character = "character_6",
            order = 0,
            movieId = 9874,
            imagePath = "/7nhbhjh1t.jpg"
        )
        val castList = listOf(
            cast1,
            cast2,
            cast3,
            cast4,
            cast5,
            cast6,
        )

        // when
        runBlocking { dao.add(cast = castList) }
        val result = runBlocking { dao.getCastOfMovie(movieId = 9876) }

        // then
        assertEquals(4, result.size)
        assertEquals(cast4, result[0])
        assertEquals(cast2, result[1])
        assertEquals(cast1, result[2])
        assertEquals(cast3, result[3])
    }

    @Test
    fun delete() {

        // given
        val cast1 = MovieCastModel(
            id = 1231,
            name = "name_1",
            character = "character_1",
            order = 5,
            movieId = 9876,
            imagePath = "/7nhbhjh19.jpg"
        )
        val cast2 = MovieCastModel(
            id = 1232,
            name = "name_2",
            character = "character_2",
            order = 3,
            movieId = 9876,
            imagePath = "/7nhbhjh12.jpg"
        )
        val cast3 = MovieCastModel(
            id = 1233,
            name = "name_3",
            character = "character_3",
            order = 6,
            movieId = 9876,
            imagePath = "/7nhbhjh18.jpg"
        )
        val cast4 = MovieCastModel(
            id = 1234,
            name = "name_4",
            character = "character_4",
            order = 2,
            movieId = 9876,
            imagePath = "/7nhbhjh19.jpg"
        )
        val castList = listOf(
            cast1,
            cast2,
            cast3,
            cast4,
        )

        // when
        runBlocking { dao.add(cast = castList) }
        val resultBeforeDelete = runBlocking { dao.getCastOfMovie(movieId = 9876) }

        runBlocking { dao.delete(movieCast = cast2) }

        val resultAfterDelete = runBlocking { dao.getCastOfMovie(movieId = 9876) }

        // then
        assertEquals(4, resultBeforeDelete.size)
        assertEquals(cast4, resultBeforeDelete[0])
        assertEquals(cast2, resultBeforeDelete[1])
        assertEquals(cast1, resultBeforeDelete[2])
        assertEquals(cast3, resultBeforeDelete[3])

        assertEquals(3, resultAfterDelete.size)
        assertEquals(cast4, resultAfterDelete[0])
        assertEquals(cast1, resultAfterDelete[1])
        assertEquals(cast3, resultAfterDelete[2])
    }
}
