package com.example.cropdoc.activity_dashboard.domain

import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun addActivity(activity: PlantingActivity)
    suspend fun deleteActivity(activity: PlantingActivity)
    suspend fun updateActivity(activity: PlantingActivity)
    suspend fun getActivityById(id:Long) : Flow<PlantingActivity>
    suspend fun getAllActivities(): Flow<List<PlantingActivity>>
    suspend fun addDailyActivity(dailyActivity: DailyActivity)
    suspend fun getDailyActivitiesForPlantingActivity(plantingActivityId: Long): Flow<List<DailyActivity>>
    suspend fun updateDailyActivity(dailyActivity: DailyActivity)
    suspend fun deleteDailyActivity(dailyActivity: DailyActivity)
}