package com.turingheights.domain.models

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: String) : Resource<T>
    data class Loading<T>(val data: String? = null) : Resource<T>
}