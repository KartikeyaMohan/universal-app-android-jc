package com.kmpstudios.universalAppJc.data.models.movies

import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("list")
    val list: List<MovieData>? = null,
    @SerialName("meta")
    val meta: PagerMetaData? = null
)