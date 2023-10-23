package fr.leboncoin.bcalbums.feature_albums.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.leboncoin.bcalbums.feature_albums.data.cache.entity.AlbumEntity

@Dao
interface AlbumsDao {

    @Query("SELECT * FROM AlbumEntity")
    suspend fun getCachedAlbums(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(users: List<AlbumEntity>): List<Long>
}