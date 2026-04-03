package com.turingheights.feeds.state

import com.turingheights.domain.models.Character

data class HomeScreenState(
    val characters: List<Character> = emptyList(),
    val favourites: List<Int> = emptyList(),
    val selected: List<Int> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isLoadingMore: Boolean = false,
)
