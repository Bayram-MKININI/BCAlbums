package fr.leboncoin.bcalbums.feature_albums.domain.repository

import fr.leboncoin.bcalbums.common.util.Resource
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    fun fetchAlbums(): Flow<Resource<List<Album>>>
}