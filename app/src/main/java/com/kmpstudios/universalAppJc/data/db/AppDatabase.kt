package com.kmpstudios.universalAppJc.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun locationDao(): LocationDao

    companion object {
        const val DATABASE_NAME = "universal_db"
    }
}