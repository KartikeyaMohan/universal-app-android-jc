package com.kmpstudios.universalAppJc.domain.useCases.locations

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.domain.repository.LocationRepository

class PostLocationsUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationRequest: LocationRequest): GenericResponse<MessageResponse> =
        locationRepository.postLocations(locationRequest)
}