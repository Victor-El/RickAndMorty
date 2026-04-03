package com.turingheights.data.network.dtos

sealed interface RemoteResource<T> {

    data object NoNetwork: RemoteResource<Nothing>

    data class NetworkError(
        val code: Int,
        val message: String
    ): RemoteResource<Nothing>

    data class Failure(
        val message: String
    ): RemoteResource<Nothing>

    data object Pending: RemoteResource<Nothing>

    data class Success<T>(
        val data: T
    ): RemoteResource<T>

}