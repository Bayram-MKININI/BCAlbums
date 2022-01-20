package fr.leboncoin.bcalbums.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "albums_database"

@Database(entities = [Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

    companion object {

        fun create(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}