package fr.leboncoin.bcalbums.model

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<Album>>
}