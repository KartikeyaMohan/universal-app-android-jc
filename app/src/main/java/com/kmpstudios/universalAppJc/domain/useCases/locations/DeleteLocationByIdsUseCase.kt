package com.kmpstudios.universalAppJc.domain.useCases.locations

import com.kmpstudios.universalAppJc.domain.repository.LocationRepository

class DeleteLocationByIdsUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(ids: List<Long>) {
        locationRepository.deleteLocationByIds(ids)
    }
}