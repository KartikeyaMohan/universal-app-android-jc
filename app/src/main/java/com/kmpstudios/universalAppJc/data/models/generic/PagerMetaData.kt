package com.kmpstudios.universalAppJc.data.models.generic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagerMetaData(
    @SerialName("current_page")
    val currentPage: Int? = null,
    @SerialName("next_page")
    val nextPage: Int? = null,
    @SerialName("prev_page")
    val prevPage: Int? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_count")
    val totalCount: Int? = null
)
