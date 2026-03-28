package com.kmpstudios.universalAppJc.domain.useCases.locations

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationsPagedUseCase(private val locationRepository: LocationRepository) {

    fun execute(): Flow<PagingData<LocationEntity>> {
        return locationRepository.getLocationsPaged()
    }
}