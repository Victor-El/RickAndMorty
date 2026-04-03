package com.turingheights.domain.models

data class Character(
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
