package com.kmpstudios.universalAppJc.domain.repository

import androidx.paging.PagingData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun insertLocation(locationEntity: LocationEntity)
    fun getLocationsPaged(): Flow<PagingData<LocationEntity>>
    suspend fun getAllLocationList(): List<LocationEntity>
    suspend fun deleteLocationByIds(ids: List<Long>)
    suspend fun clearAll()
    fun getLocationCount(): Flow<Int>
    suspend fun postLocations(locationRequest: LocationRequest): GenericResponse<MessageResponse>
}