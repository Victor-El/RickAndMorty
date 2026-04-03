package com.turingheights.data.di

import android.content.Context
import com.turingheights.data.BASE_URL
import com.turingheights.data.CONNECT_TIMEOUT
import com.turingheights.data.READ_TIMEOUT
import com.turingheights.data.WRITE_TIMEOUT
import com.turingheights.data.db.RickAndMortyDatabase
import com.turingheights.data.network.RickAndMortyService
import com.turingheights.data.repositories.RickAndMortyCharacterRepository
import com.turingheights.domain.repositories.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        rickAndMortyCharacterRepository: RickAndMortyCharacterRepository
    ): CharacterRepository

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
            return RickAndMortyDatabase.getInstance(context)
        }

        @Provides
        @Singleton
        fun provideEntityDao(database: RickAndMortyDatabase) = database.entityDao()

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
            return okHttpClient.build()
        }

        @Provides
        @Singleton
        fun provideRickAndMortyService(
            okHttpClient: OkHttpClient,
        ): RickAndMortyService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()
                .create(RickAndMortyService::class.java)
        }

    }


}