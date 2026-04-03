package com.turingheights.domain.repositories

import com.turingheights.domain.models.Character
import com.turingheights.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun fetchCharacters(page: Int): Flow<Resource<List<Character>>>

    suspend fun fetchFavourites(): Flow<List<Character>>

    suspend fun updateCharacter(character: Character)

}