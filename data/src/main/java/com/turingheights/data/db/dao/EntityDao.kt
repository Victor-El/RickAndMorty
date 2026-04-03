package com.turingheights.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.turingheights.data.PAGE_LIMIT
import com.turingheights.data.db.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entity: CharacterEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: CharacterEntity)

    @Query("SELECT * FROM CharacterEntity LIMIT $PAGE_LIMIT OFFSET :page * $PAGE_LIMIT")
    fun fetchCharacters(page: Int): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM CharacterEntity WHERE favourite = 1")
    fun fetchFavourites(): Flow<List<CharacterEntity>>

}