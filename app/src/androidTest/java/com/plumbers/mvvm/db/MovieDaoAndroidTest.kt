package com.plumbers.mvvm.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plumbers.mvvm.data.db.AppDatabase
import com.plumbers.mvvm.data.db.MovieDao
import com.plumbers.mvvm.data.model.MovieModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoAndroidTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = appDatabase.movieDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeAndRead() {

        // given
        val movie1 = MovieModel(
            id = 1231,
            posterImagePath = "poster_image_path_1",
            backdropImagePath = "backdrop_image_path_1",
            title = "title_1",
            overview = "overview_1",
            average = 12.23,
            popularity = 120.23,
        )
        val movie2 = MovieModel(
            id = 1232,
            posterImagePath = "poster_image_path_2",
            backdropImagePath = "backdrop_image_path_2",
            title = "title_2",
            overview = "overview_2",
            average = 12.23,
            popularity = 2.23,
        )
        val movie3 = MovieModel(
            id = 1233,
            posterImagePath = "poster_image_path_3",
            backdropImagePath = "backdrop_image_path_3",
            title = "title_3",
            overview = "overview_3",
            average = 12.23,
            popularity = 56.23,
        )
        val movie4 = MovieModel(
            id = 1234,
            posterImagePath = "poster_image_path_4",
            backdropImagePath = "backdrop_image_path_4",
            title = "title_4",
            overview = "overview_4",
            average = 12.23,
            popularity = 83.23,
        )
        val movie5 = MovieModel(
            id = 1235,
            posterImagePath = "poster_image_path_5",
            backdropImagePath = "backdrop_image_path_5",
            title = "title_5",
            overview = "overview_5",
            average = 12.23,
            popularity = 75.23,
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
            movie5,
        )

        // when
        runBlocking { dao.add(movies = movieList) }
        val result = runBlocking { dao.getPopularMovies(offset = 2) }

        // then
        assertEquals(3, result.size)
        assertEquals(movie5, result[0])
        assertEquals(movie3, result[1])
        assertEquals(movie2, result[2])
    }

    @Test
    fun delete() {

        // given
        val movie1 = MovieModel(
            id = 1231,
            posterImagePath = "poster_image_path_1",
            backdropImagePath = "backdrop_image_path_1",
            title = "title_1",
            overview = "overview_1",
            average = 12.23,
            popularity = 120.23,
        )
        val movie2 = MovieModel(
            id = 1232,
            posterImagePath = "poster_image_path_2",
            backdropImagePath = "backdrop_image_path_2",
            title = "title_2",
            overview = "overview_2",
            average = 12.23,
            popularity = 2.23,
        )
        val movie3 = MovieModel(
            id = 1233,
            posterImagePath = "poster_image_path_3",
            backdropImagePath = "backdrop_image_path_3",
            title = "title_3",
            overview = "overview_3",
            average = 12.23,
            popularity = 56.23,
        )
        val movie4 = MovieModel(
            id = 1234,
            posterImagePath = "poster_image_path_4",
            backdropImagePath = "backdrop_image_path_4",
            title = "title_4",
            overview = "overview_4",
            average = 12.23,
            popularity = 83.23,
        )
        val movie5 = MovieModel(
            id = 1235,
            posterImagePath = "poster_image_path_5",
            backdropImagePath = "backdrop_image_path_5",
            title = "title_5",
            overview = "overview_5",
            average = 12.23,
            popularity = 75.23,
        )
        val movieList = listOf(
            movie1,
            movie2,
            movie3,
            movie4,
            movie5,
        )

        // when
        runBlocking { dao.add(movies = movieList) }
        val resultBeforeDelete = runBlocking { dao.getPopularMovies(offset = 0) }

        runBlocking { dao.delete(movie = movie1) }
        runBlocking { dao.delete(movie = movie3) }

        val resultAfterDelete = runBlocking { dao.getPopularMovies(offset = 0) }

        // then
        assertEquals(5, resultBeforeDelete.size)
        assertEquals(movie1, resultBeforeDelete[0])
        assertEquals(movie4, resultBeforeDelete[1])
        assertEquals(movie5, resultBeforeDelete[2])
        assertEquals(movie3, resultBeforeDelete[3])
        assertEquals(movie2, resultBeforeDelete[4])

        assertEquals(3, resultAfterDelete.size)
        assertEquals(movie4, resultAfterDelete[0])
        assertEquals(movie5, resultAfterDelete[1])
        assertEquals(movie2, resultAfterDelete[2])
    }
}
