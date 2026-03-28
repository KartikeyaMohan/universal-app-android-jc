package com.kmpstudios.universalAppJc.data.models.locations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("accuracy") val accuracy: Float
)

fun LocationEntity.toLocationData() = LocationData(
    date = date,
    time = time,
    latitude = latitude,
    longitude = longitude,
    accuracy = accuracy
)