package fr.leboncoin.bcalbums.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Album(

    @Json(name = "id")
    @PrimaryKey
    val id: Int = 0,

    @Json(name = "albumId")
    @ColumnInfo(name = "albumId")
    val albumId: Int = 0,

    @Json(name = "thumbnailUrl")
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String = "",

    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String = "",

    @Json(name = "url")
    @ColumnInfo(name = "url")
    val url: String = ""
)