package com.turingheights.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turingheights.domain.models.Character

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val specie: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val favourite: Boolean,
)

fun CharacterEntity.toCharacter() = Character(
    id = id,
    name = name,
    status = status,
    specie = specie,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    favourite = favourite,
)

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    specie = specie,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    favourite = favourite,
)