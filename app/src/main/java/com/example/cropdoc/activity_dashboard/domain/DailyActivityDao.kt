package com.example.cropdoc.activity_dashboard.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DailyActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addDailyActivity(dailyActivity: DailyActivity)

    @Query("SELECT * FROM `daily-activity-table` WHERE plantingActivityId = :plantingActivityId ORDER BY date DESC")
    abstract fun getDailyActivitiesForPlantingActivity(plantingActivityId: Long): Flow<List<DailyActivity>>

    @Update
    abstract suspend fun updateDailyActivity(dailyActivity: DailyActivity)

    @Delete
    abstract suspend fun deleteDailyActivity(dailyActivity: DailyActivity)
}