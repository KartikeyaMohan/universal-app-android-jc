package com.kmpstudios.universalAppJc.data.models.locations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val time: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float
)