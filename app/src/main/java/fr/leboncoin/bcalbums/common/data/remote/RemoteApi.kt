package fr.leboncoin.bcalbums.common.data.remote

import fr.leboncoin.bcalbums.feature_albums.data.remote.dto.AlbumDTO
import retrofit2.http.GET

interface RemoteApi {
    @GET("img/shared/technical-test.json")
    suspend fun fetchAlbums(): List<AlbumDTO>
}