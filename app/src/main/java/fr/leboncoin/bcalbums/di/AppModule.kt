package fr.leboncoin.bcalbums.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.leboncoin.bcalbums.model.*
import fr.leboncoin.bcalbums.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideApiService(retrofit = get()) }
    single { provideNetworkHelper(context = androidContext()) }
    single { provideDatabase(context = androidContext()) }
    single { provideDao(database = get()) }
    single { return@single DataRepository(apiService = get(), albumDao = get()) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .followRedirects(true)
        .build()

} else OkHttpClient
    .Builder()
    .followRedirects(true)
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        )
    )
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

private fun provideDatabase(context: Context): AppDatabase = AppDatabase.create(context)

fun provideDao(database: AppDatabase): AlbumsDao = database.albumsDao()