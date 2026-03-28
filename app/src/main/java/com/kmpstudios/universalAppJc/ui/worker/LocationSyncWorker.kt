package com.kmpstudios.universalAppJc.ui.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.models.locations.toLocationData
import com.kmpstudios.universalAppJc.domain.useCases.locations.DeleteLocationByIdsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetAllLocationListUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.PostLocationsUseCase
import com.kmpstudios.universalAppJc.ui.services.ServiceStateHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class LocationSyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getAllLocationListUseCase: GetAllLocationListUseCase,
    private val deleteLocationByIdsUseCase: DeleteLocationByIdsUseCase,
    private val postLocationsUseCase: PostLocationsUseCase,
    private val serviceStateHolder: ServiceStateHolder
): CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        return try {
            val locations = getAllLocationListUseCase.execute()
            if (locations.isEmpty()) {
                stopSyncIfLocationsEmpty()
                return Result.success()
            }

            val locationRequest = LocationRequest(locations.map { it.toLocationData() })
            val response: GenericResponse<MessageResponse> = postLocationsUseCase.execute(locationRequest)
            if (response.isSuccess()) {
                val ids = locations.map { it.id }
                deleteLocationByIdsUseCase.execute(ids)
                stopSyncIfLocationsEmpty()
                Result.success()
            }
            else {
                Result.failure()
            }
        }
        catch (exception: Exception) {
            Result.failure()
        }
    }

    private fun stopSyncIfLocationsEmpty() {
        if (serviceStateHolder.isRunning.value.not()) {
            WorkManager.getInstance(context).cancelUniqueWork(LocationSyncScheduler.WORK_NAME)
        }
    }
}