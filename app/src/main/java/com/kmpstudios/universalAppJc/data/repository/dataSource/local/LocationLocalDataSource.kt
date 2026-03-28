package com.kmpstudios.universalAppJc.data.repository.dataSource.local

import androidx.paging.PagingSource
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSource {

    suspend fun insertLocation(locationEntity: LocationEntity)
    fun getLocationsPaged(): PagingSource<Int, LocationEntity>
    suspend fun getAllLocationList(): List<LocationEntity>
    suspend fun deleteLocationByIds(ids: List<Long>)
    suspend fun clearAll()
    fun getLocationCount(): Flow<Int>
}