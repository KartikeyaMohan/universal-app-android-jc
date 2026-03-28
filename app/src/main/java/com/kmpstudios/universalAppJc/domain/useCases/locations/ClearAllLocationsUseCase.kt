package com.kmpstudios.universalAppJc.domain.useCases.locations

import com.kmpstudios.universalAppJc.domain.repository.LocationRepository

class ClearAllLocationsUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute() {
        locationRepository.clearAll()
    }
}