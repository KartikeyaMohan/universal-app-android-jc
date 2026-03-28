package com.kmpstudios.universalAppJc.domain.useCases.locations

import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.domain.repository.LocationRepository

class InsertLocationsUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationEntity: LocationEntity) {
        locationRepository.insertLocation(locationEntity)
    }
}