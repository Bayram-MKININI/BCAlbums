package fr.leboncoin.bcalbums.model

import fr.leboncoin.bcalbums.utils.Error
import fr.leboncoin.bcalbums.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class DataRepository(
    private val apiService: ApiService,
    private val albumDao: AlbumsDao
) {

    suspend fun fetchAlbums(): Flow<Resource<List<Album>>> {

        return flow {

            val cachedAlbums = fetchAlbumsCached()

            if (cachedAlbums.isNotEmpty())
                emit(Resource.success(cachedAlbums))
            else
                emit(Resource.loading())

            var result = Resource.error<List<Album>>(error = Error.SYSTEM)

            try {

                val response = apiService.getAlbums()

                if (response.isSuccessful && response.body() != null) {

                    response.body()?.let { albumsList ->

                        val inserts = albumDao.insertAll(albumsList).filter { it > 0 }

                        result =
                            if (inserts.isNotEmpty())
                                Resource.success(albumsList)
                            else
                                Resource.success(arrayListOf())
                    }
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            emit(result)

        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchAlbumsCached() = albumDao.getAllAlbums()
}