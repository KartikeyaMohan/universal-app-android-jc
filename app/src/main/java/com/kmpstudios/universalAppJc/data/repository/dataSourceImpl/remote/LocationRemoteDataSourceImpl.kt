package com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.LocationRemoteDataSource
import retrofit2.Response

class LocationRemoteDataSourceImpl(private val apiService: ApiService): LocationRemoteDataSource {

    override suspend fun postLocations(locationRequest: LocationRequest): Response<GenericResponse<MessageResponse>> {
        return apiService.postLocations(locationRequest)
    }
}