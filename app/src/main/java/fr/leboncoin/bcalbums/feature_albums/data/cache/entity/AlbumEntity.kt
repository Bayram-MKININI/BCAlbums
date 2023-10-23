package fr.leboncoin.bcalbums.feature_albums.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album

@Entity
data class AlbumEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "albumId")
    val albumId: Int = 0,
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "url")
    val url: String = ""
){
    fun toAlbum() = Album(
        id = id,
        albumId = albumId,
        title = title,
        thumbnailUrl = thumbnailUrl,
        url = url
    )
}