package fr.leboncoin.bcalbums.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.bcalbums.BuildConfig
import fr.leboncoin.bcalbums.common.BASE_ENDPOINT
import fr.leboncoin.bcalbums.common.data.remote.RemoteApi
import fr.leboncoin.bcalbums.feature_albums.data.cache.AlbumsDao
import fr.leboncoin.bcalbums.feature_albums.data.cache.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .followRedirects(true)
            .build()
    } else OkHttpClient.Builder()
        .followRedirects(true)
        .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        )

    @Provides
    @Singleton
    fun provideApi(
        builder: Retrofit.Builder
    ): RemoteApi = builder.build().create(RemoteApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): AppDatabase = AppDatabase.invoke(context)

    @Provides
    @Singleton
    fun provideDao(
        database: AppDatabase
    ): AlbumsDao = database.albumsDao()
}