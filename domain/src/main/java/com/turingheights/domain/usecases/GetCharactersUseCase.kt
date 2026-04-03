package com.turingheights.domain.usecases

import com.turingheights.domain.models.Character
import com.turingheights.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke(page: Int): Flow<List<Character>> {
        return repository.fetchCharacters(page)
    }

}