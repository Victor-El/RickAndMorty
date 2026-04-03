package com.turingheights.data.network

import com.turingheights.data.network.dtos.RemoteResource
import com.turingheights.data.network.dtos.res.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse


}