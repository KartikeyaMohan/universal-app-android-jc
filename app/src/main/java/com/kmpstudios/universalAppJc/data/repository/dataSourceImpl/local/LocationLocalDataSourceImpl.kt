package com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.local

import androidx.paging.PagingSource
import com.kmpstudios.universalAppJc.data.db.LocationDao
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.data.repository.dataSource.local.LocationLocalDataSource
import kotlinx.coroutines.flow.Flow

class LocationLocalDataSourceImpl (private val locationDao: LocationDao): LocationLocalDataSource {

    override suspend fun insertLocation(locationEntity: LocationEntity) {
        locationDao.insertLocation(locationEntity)
    }

    override fun getLocationsPaged(): PagingSource<Int, LocationEntity> =
        locationDao.getLocationsPaged()

    override suspend fun getAllLocationList(): List<LocationEntity> =
        locationDao.getAllLocationsList()

    override suspend fun deleteLocationByIds(ids: List<Long>) {
        locationDao.deleteLocationsByIds(ids)
    }

    override suspend fun clearAll() {
        locationDao.clearAll()
    }

    override fun getLocationCount(): Flow<Int> = locationDao.getLocationCount()
}