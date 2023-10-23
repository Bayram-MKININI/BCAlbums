package fr.leboncoin.bcalbums.feature_albums.domain.usescases

import fr.leboncoin.bcalbums.feature_albums.domain.repository.AlbumsRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {
    operator fun invoke() = repository.fetchAlbums()
}