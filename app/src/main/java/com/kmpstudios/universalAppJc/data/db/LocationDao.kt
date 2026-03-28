package com.kmpstudios.universalAppJc.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM locations ORDER BY date DESC, time DESC")
    fun getLocationsPaged(): PagingSource<Int, LocationEntity>

    @Query("SELECT * FROM locations ORDER BY date DESC, time DESC")
    suspend fun getAllLocationsList(): List<LocationEntity>

    @Query("DELETE FROM locations WHERE id IN (:ids)")
    suspend fun deleteLocationsByIds(ids: List<Long>)

    @Query("DELETE FROM locations")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM locations")
    fun getLocationCount(): Flow<Int>
}