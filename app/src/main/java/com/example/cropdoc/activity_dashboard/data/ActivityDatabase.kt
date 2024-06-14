package com.example.cropdoc.activity_dashboard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cropdoc.activity_dashboard.domain.DailyActivity
import com.example.cropdoc.activity_dashboard.domain.DailyActivityDao
import com.example.cropdoc.activity_dashboard.domain.PlantingActivity
import com.example.cropdoc.activity_dashboard.domain.PlantingActivityDao

@Database(
    entities = [PlantingActivity::class, DailyActivity::class],
    version = 2,
    exportSchema = false
)
abstract class PlantingActivityDatabase : RoomDatabase() {
    abstract fun PlantingActivityDao(): PlantingActivityDao
    abstract fun dailyActivityDao(): DailyActivityDao
}