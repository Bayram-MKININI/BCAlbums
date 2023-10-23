package fr.leboncoin.bcalbums.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.leboncoin.bcalbums.feature_albums.data.cache.AlbumsDao
import fr.leboncoin.bcalbums.feature_albums.data.cache.AppDatabase
import fr.leboncoin.bcalbums.feature_albums.data.cache.entity.AlbumEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var albumsDao: AlbumsDao
    private lateinit var db: AppDatabase

    @Before
    internal fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        albumsDao = db.albumsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadDB() = runBlocking {

        val insertedAlbums = listOf(
            AlbumEntity(
                albumId = 1,
                id = 1,
                title = "Thriller",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            ),
            AlbumEntity(
                albumId = 2,
                id = 2,
                title = "Dangerous",
                url = "https://via.placeholder.com/600/771796",
                thumbnailUrl = "https://via.placeholder.com/150/771796"
            )
        )

        albumsDao.insertAlbums(insertedAlbums)
        val returnedAlbums = albumsDao.getCachedAlbums()

        Assert.assertTrue(returnedAlbums.size == 2)
        Assert.assertEquals(returnedAlbums[0].title, insertedAlbums[0].title)
    }
}