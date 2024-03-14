package com.example.cropdoc.data

import kotlinx.coroutines.flow.Flow

class ActivityRepository(private val activityDao: PlantingActivityDao) {

    suspend fun addActivity(activity:PlantingActivity){
        activityDao.addActivity(activity)
    }

    fun getAllActivities(): Flow<List<PlantingActivity>> = activityDao.getAllActivities()

    fun getActivityById(id:Long) : Flow<PlantingActivity> {
        return activityDao.getActivityById(id)
    }

    suspend fun updateActivity(activity:PlantingActivity){
        activityDao.updateActivity(activity)
    }

    suspend fun deleteActivity(activity:PlantingActivity){
        activityDao.deleteActivity(activity)
    }

}