package fr.leboncoin.bcalbums.feature_albums.data.repository

import fr.leboncoin.bcalbums.common.data.remote.RemoteApi
import fr.leboncoin.bcalbums.common.util.ErrorType
import fr.leboncoin.bcalbums.common.util.Resource
import fr.leboncoin.bcalbums.common.util.Resource.Error
import fr.leboncoin.bcalbums.common.util.Resource.Loading
import fr.leboncoin.bcalbums.common.util.Resource.Success
import fr.leboncoin.bcalbums.feature_albums.data.cache.AlbumsDao
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import fr.leboncoin.bcalbums.feature_albums.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumsRepositoryImp @Inject constructor(
    private val api: RemoteApi,
    private val albumDao: AlbumsDao
) : AlbumsRepository {

    override fun fetchAlbums(): Flow<Resource<List<Album>>> = flow {

        emit(Loading())
        val cachedAlbums = fetchAlbumsCached()
        emit(Loading(data = cachedAlbums.map { it.toAlbum() }))

        try {
            val response = api.fetchAlbums()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let { albumsList ->
                    albumDao.insertAlbums(
                        albumsList.map { it.toAlbumEntity() }
                    ).filter {
                        it > 0
                    }.also { inserts ->
                        if (inserts.isNotEmpty()) {
                            emit(
                                Success(albumDao.getCachedAlbums().map { it.toAlbum() })
                            )
                        }
                    }
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Error(dataError = ErrorType.NETWORK_ERROR))
        }
    }

    private suspend fun fetchAlbumsCached() = albumDao.getCachedAlbums()
}