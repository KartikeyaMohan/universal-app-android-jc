package com.kmpstudios.universalAppJc.viewmodels

import androidx.paging.PagingData
import androidx.work.WorkManager
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.domain.useCases.locations.ClearAllLocationsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.DeleteLocationByIdsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetAllLocationListUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationCountUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationsPagedUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.PostLocationsUseCase
import com.kmpstudios.universalAppJc.ui.services.ServiceStateHolder
import com.kmpstudios.universalAppJc.ui.viewmodels.LocationViewModel
import com.kmpstudios.universalAppJc.ui.worker.LocationSyncScheduler
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val workManager: WorkManager = mockk(relaxed = true)
    private val locationSyncScheduler: LocationSyncScheduler = mockk(relaxed = true)
    private val serviceStateHolder: ServiceStateHolder = mockk()
    private val getLocationsPagedUseCase: GetLocationsPagedUseCase = mockk()
    private val getLocationCountUseCase: GetLocationCountUseCase = mockk()
    private val clearAllLocationsUseCase: ClearAllLocationsUseCase = mockk()
    private val postLocationsUseCase: PostLocationsUseCase = mockk()
    private val deleteLocationByIdsUseCase: DeleteLocationByIdsUseCase = mockk()
    private val getAllLocationListUseCase: GetAllLocationListUseCase = mockk()

    private fun createViewModel(): LocationViewModel {
        every { serviceStateHolder.isRunning } returns MutableStateFlow(false)
        coEvery { getLocationsPagedUseCase.execute() } returns flowOf(PagingData.empty())
        coEvery { getLocationCountUseCase.execute() } returns flowOf(0)
        return LocationViewModel(
            workManager = workManager,
            locationSyncScheduler = locationSyncScheduler,
            serviceStateHolder = serviceStateHolder,
            getLocationsPagedUseCase = getLocationsPagedUseCase,
            getLocationCountUseCase = getLocationCountUseCase,
            clearAllLocationsUseCase = clearAllLocationsUseCase,
            postLocationsUseCase = postLocationsUseCase,
            deleteLocationByIdsUseCase = deleteLocationByIdsUseCase,
            getAllLocationListUseCase = getAllLocationListUseCase
        )
    }

    @Test
    fun `clearAll invokes use case`() = runTest(testDispatcher) {
        coEvery { clearAllLocationsUseCase.execute() } returns Unit
        val vm = createViewModel()
        vm.clearAll()
        advanceUntilIdle()
        coVerify(exactly = 1) { clearAllLocationsUseCase.execute() }
    }

    @Test
    fun `postLocations skips remote when local list empty`() = runTest(testDispatcher) {
        coEvery { getAllLocationListUseCase.execute() } returns emptyList()
        val vm = createViewModel()
        vm.postLocations()
        advanceUntilIdle()
        coVerify(exactly = 0) { postLocationsUseCase.execute(any()) }
    }

    @Test
    fun `postLocations on success deletes synced ids`() = runTest(testDispatcher) {
        val e1 = LocationEntity(1L, "d", "t", 1.0, 2.0, 3f)
        val e2 = LocationEntity(2L, "d", "t", 4.0, 5.0, 6f)
        coEvery { getAllLocationListUseCase.execute() } returns listOf(e1, e2)
        coEvery { postLocationsUseCase.execute(any()) } returns GenericResponse(
            status = 200,
            data = MessageResponse("ok")
        )
        coEvery { deleteLocationByIdsUseCase.execute(any()) } returns Unit

        val vm = createViewModel()
        vm.postLocations()
        advanceUntilIdle()

        coVerify(exactly = 1) { deleteLocationByIdsUseCase.execute(listOf(1L, 2L)) }
    }
}
