package com.kmpstudios.universalAppJc.data.repository.dataSource.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import retrofit2.Response

interface LocationRemoteDataSource {

    suspend fun postLocations(locationRequest: LocationRequest): Response<GenericResponse<MessageResponse>>
}