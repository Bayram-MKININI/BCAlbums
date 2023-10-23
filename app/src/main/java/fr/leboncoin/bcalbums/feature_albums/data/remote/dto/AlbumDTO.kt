package fr.leboncoin.bcalbums.feature_albums.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.leboncoin.bcalbums.feature_albums.data.cache.entity.AlbumEntity

@JsonClass(generateAdapter = true)
data class AlbumDTO(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "albumId")
    val albumId: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String = "",
    @Json(name = "url")
    val url: String = ""
) {
    fun toAlbumEntity() = AlbumEntity(
        id = id,
        albumId = albumId,
        title = title,
        thumbnailUrl = thumbnailUrl,
        url = url
    )
}