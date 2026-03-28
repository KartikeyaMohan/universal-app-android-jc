package com.kmpstudios.universalAppJc.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.models.locations.toLocationData
import com.kmpstudios.universalAppJc.domain.useCases.locations.ClearAllLocationsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.DeleteLocationByIdsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetAllLocationListUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationCountUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationsPagedUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.PostLocationsUseCase
import com.kmpstudios.universalAppJc.ui.services.LocationForegroundService
import com.kmpstudios.universalAppJc.ui.services.ServiceStateHolder
import com.kmpstudios.universalAppJc.ui.utils.WorkManagerHelper.isPeriodicWorkScheduled
import com.kmpstudios.universalAppJc.ui.worker.LocationSyncScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val locationSyncScheduler: LocationSyncScheduler,
    private val serviceStateHolder: ServiceStateHolder,
    private val getLocationsPagedUseCase: GetLocationsPagedUseCase,
    private val getLocationCountUseCase: GetLocationCountUseCase,
    private val clearAllLocationsUseCase: ClearAllLocationsUseCase,
    private val postLocationsUseCase: PostLocationsUseCase,
    private val deleteLocationByIdsUseCase: DeleteLocationByIdsUseCase,
    private val getAllLocationListUseCase: GetAllLocationListUseCase,
): ViewModel() {

    val isServiceRunning: StateFlow<Boolean> = serviceStateHolder.isRunning

    fun startService(context: Context) {
        viewModelScope.launch {
            val intent = Intent(context, LocationForegroundService::class.java).apply {
                action = LocationForegroundService.ACTION_START
            }
            ContextCompat.startForegroundService(context, intent)
            if (workManager.isPeriodicWorkScheduled(LocationSyncScheduler.WORK_NAME).not()) {
                locationSyncScheduler.schedule()
            }
        }
    }

    fun stopService(context: Context) {
        viewModelScope.launch {
            val intent = Intent(context, LocationForegroundService::class.java).apply {
                action = LocationForegroundService.ACTION_STOP
            }
            context.startService(intent)
        }
    }

    val pagedLocations: Flow<PagingData<LocationEntity>> = getLocationsPagedUseCase
        .execute()
        .cachedIn(viewModelScope)

    val locationCount: StateFlow<Int> = getLocationCountUseCase
        .execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    fun clearAll() {
        viewModelScope.launch {
            clearAllLocationsUseCase.execute()
        }
    }

    fun postLocations() {
        viewModelScope.launch {
            val locations = getAllLocationListUseCase.execute()
            if (locations.isEmpty()) {
                return@launch
            }
            val locationRequest = LocationRequest(locations.map { it.toLocationData() })
            val response = postLocationsUseCase.execute(locationRequest)
            if (response.isSuccess()) {
                val ids = locations.map { it.id }
                deleteLocationByIdsUseCase.execute(ids)
            }
            else {
                Log.i("KMX", response.errors.toString())
            }
        }
    }
}