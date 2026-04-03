package com.turingheights.domain.usecases

import com.turingheights.domain.models.Character
import com.turingheights.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritesUseCase(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(): Flow<List<Character>> {
        return repository.fetchFavourites()
    }
}