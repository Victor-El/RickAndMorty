package com.turingheights.data.repositories

import com.turingheights.data.db.dao.EntityDao
import com.turingheights.data.db.entities.toCharacter
import com.turingheights.data.db.entities.toEntity
import com.turingheights.data.network.RickAndMortyService
import com.turingheights.data.network.dtos.res.toCharacterEntityList
import com.turingheights.data.network.dtos.res.toCharacterList
import com.turingheights.domain.models.Character
import com.turingheights.domain.models.Resource
import com.turingheights.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RickAndMortyCharacterRepository @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val entityDao: EntityDao,
): CharacterRepository {

    override suspend fun fetchCharacters(page: Int): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())
        val result = try {
            val res = rickAndMortyService.getCharacters(page)
            entityDao.insert(*res.toCharacterEntityList().toTypedArray())
            Resource.Success(res.toCharacterList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong")
        }

        emit(result)
    }

    override suspend fun fetchFavourites(): Flow<List<Character>> {
        return entityDao.fetchFavourites().map { it.map { entity -> entity.toCharacter() } }
    }

    override suspend fun updateCharacter(character: Character) {
        entityDao.update(character.toEntity())
    }

}