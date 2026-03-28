package com.kmpstudios.universalAppJc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.repository.dataSource.local.LocationLocalDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.LocationRemoteDataSource
import com.kmpstudios.universalAppJc.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val locationLocalDataSource: LocationLocalDataSource,
    private val locationRemoteDataSource: LocationRemoteDataSource
): BaseRepository(), LocationRepository {

    override suspend fun insertLocation(locationEntity: LocationEntity) {
        locationLocalDataSource.insertLocation(locationEntity)
    }

    override fun getLocationsPaged(): Flow<PagingData<LocationEntity>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            prefetchDistance = 10
        )
    ) {
        locationLocalDataSource.getLocationsPaged()
    }.flow

    override suspend fun getAllLocationList(): List<LocationEntity> =
        locationLocalDataSource.getAllLocationList()

    override suspend fun deleteLocationByIds(ids: List<Long>) {
        locationLocalDataSource.deleteLocationByIds(ids)
    }

    override suspend fun clearAll() {
        locationLocalDataSource.clearAll()
    }

    override fun getLocationCount(): Flow<Int> =
        locationLocalDataSource.getLocationCount()

    override suspend fun postLocations(locationRequest: LocationRequest): GenericResponse<MessageResponse> = callApi {
        locationRemoteDataSource.postLocations(locationRequest)
    }
}