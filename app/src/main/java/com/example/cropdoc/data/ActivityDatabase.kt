package com.example.cropdoc.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlantingActivity::class],
    version = 2,
    exportSchema = false
)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun PlantingActivityDao(): PlantingActivityDao
}