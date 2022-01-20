package fr.leboncoin.bcalbums.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumsDao {

    @Query("SELECT * FROM album")
    fun getAllAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<Album>): List<Long>
}