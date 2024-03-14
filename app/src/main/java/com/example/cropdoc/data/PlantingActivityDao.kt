package com.example.cropdoc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PlantingActivityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addActivity(activityEntity: PlantingActivity)

    // Loads all activities from the activity table
    @Query("Select * from `planting-activity-table`")
    abstract fun getAllActivities(): Flow<List<PlantingActivity>>

    @Update
    abstract suspend fun updateActivity(activityEntity: PlantingActivity)

    @Delete
    abstract suspend fun deleteActivity(activityEntity: PlantingActivity)

    @Query("Select * from `planting-activity-table` where id=:id")
    abstract fun getActivityById(id:Long): Flow<PlantingActivity>
}