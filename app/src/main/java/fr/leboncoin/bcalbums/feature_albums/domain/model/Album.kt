package fr.leboncoin.bcalbums.feature_albums.domain.model

data class Album(
    val id: Int = 0,
    val albumId: Int = 0,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
)