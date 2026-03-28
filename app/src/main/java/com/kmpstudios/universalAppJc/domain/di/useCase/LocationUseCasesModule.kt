package com.kmpstudios.universalAppJc.domain.di.useCase

import com.kmpstudios.universalAppJc.domain.repository.LocationRepository
import com.kmpstudios.universalAppJc.domain.useCases.locations.ClearAllLocationsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.DeleteLocationByIdsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetAllLocationListUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationCountUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.GetLocationsPagedUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.InsertLocationsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.locations.PostLocationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationUseCasesModule {

    @Singleton
    @Provides
    fun providesInsertLocationUseCase(locationRepository: LocationRepository): InsertLocationsUseCase {
        return InsertLocationsUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesGetLocationsPagedUseCase(locationRepository: LocationRepository): GetLocationsPagedUseCase {
        return GetLocationsPagedUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesGetAllLocationListUseCase(locationRepository: LocationRepository): GetAllLocationListUseCase {
        return GetAllLocationListUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteLocationByIdsUseCase(locationRepository: LocationRepository): DeleteLocationByIdsUseCase {
        return DeleteLocationByIdsUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesClearAllLocationsUseCase(locationRepository: LocationRepository): ClearAllLocationsUseCase {
        return ClearAllLocationsUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesGetLocationCountUseCase(locationRepository: LocationRepository): GetLocationCountUseCase {
        return GetLocationCountUseCase(locationRepository)
    }

    @Singleton
    @Provides
    fun providesPostLocationsUseCase(locationRepository: LocationRepository): PostLocationsUseCase {
        return PostLocationsUseCase(locationRepository)
    }
}