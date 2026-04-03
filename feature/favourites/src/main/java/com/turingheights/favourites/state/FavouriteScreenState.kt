package com.turingheights.favourites.state

import com.turingheights.domain.models.Character

data class FavouriteScreenState(
    val favourites: List<Character> = emptyList(),
)
