package fr.leboncoin.bcalbums.feature_albums.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import fr.leboncoin.bcalbums.feature_albums.data.repository.AlbumsRepositoryImp
import fr.leboncoin.bcalbums.feature_albums.domain.repository.AlbumsRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumsModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAlbumsRepository(alertsRepository: AlbumsRepositoryImp): AlbumsRepository
}