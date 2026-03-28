package com.kmpstudios.universalAppJc.data.models.locations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationRequest(
    @SerialName("list") val list: List<LocationData>
)
