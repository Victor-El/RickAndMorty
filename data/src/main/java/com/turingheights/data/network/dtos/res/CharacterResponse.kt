package com.turingheights.data.network.dtos.res


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.turingheights.data.db.entities.CharacterEntity
import com.turingheights.domain.models.Character

@Keep
data class CharacterResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Result?>?
) {
    @Keep
    data class Info(
        @SerializedName("count")
        val count: Int?,
        @SerializedName("next")
        val next: String?,
        @SerializedName("pages")
        val pages: Int?,
        @SerializedName("prev")
        val prev: Any?
    )

    @Keep
    data class Result(
        @SerializedName("created")
        val created: String?,
        @SerializedName("episode")
        val episode: List<String?>?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("location")
        val location: Location?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("origin")
        val origin: Origin?,
        @SerializedName("species")
        val species: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("url")
        val url: String?
    ) {
        @Keep
        data class Location(
            @SerializedName("name")
            val name: String?,
            @SerializedName("url")
            val url: String?
        )

        @Keep
        data class Origin(
            @SerializedName("name")
            val name: String?,
            @SerializedName("url")
            val url: String?
        )
    }
}

fun CharacterResponse.toCharacterList(): List<Character> {
    return this.results?.map {
        Character(
            id = it?.id ?: 0,
            name = it?.name ?: "",
            status = it?.status ?: "",
            specie = it?.species ?: "",
            gender = it?.gender ?: "",
            origin = it?.origin?.name ?: "",
            location = it?.location?.name ?: "",
            image = it?.image ?: "",
            favourite = false,
        )
    } ?: emptyList()
}

fun CharacterResponse.toCharacterEntityList(): List<CharacterEntity> {
    return this.results?.map {
        CharacterEntity(
            id = it?.id ?: 0,
            name = it?.name ?: "",
            status = it?.status ?: "",
            specie = it?.species ?: "",
            gender = it?.gender ?: "",
            origin = it?.origin?.name ?: "",
            location = it?.location?.name ?: "",
            image = it?.image ?: "",
            favourite = false,
        )
    } ?: emptyList()
}