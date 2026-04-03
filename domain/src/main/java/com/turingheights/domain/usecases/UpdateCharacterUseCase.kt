package com.turingheights.domain.usecases

import com.turingheights.domain.models.Character
import com.turingheights.domain.repositories.CharacterRepository

class UpdateCharacterUseCase(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(character: Character) {
        repository.updateCharacter(character)
    }
}