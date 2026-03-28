package com.kmpstudios.universalAppJc.domain.useCases.locations

import com.kmpstudios.universalAppJc.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationCountUseCase(private val locationRepository: LocationRepository) {

    fun execute(): Flow<Int> = locationRepository.getLocationCount()
}