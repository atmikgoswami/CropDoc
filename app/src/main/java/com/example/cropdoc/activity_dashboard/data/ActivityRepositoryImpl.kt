package com.example.cropdoc.activity_dashboard.data


import com.example.cropdoc.activity_dashboard.domain.ActivityRepository
import com.example.cropdoc.activity_dashboard.domain.DailyActivity
import com.example.cropdoc.activity_dashboard.domain.DailyActivityDao
import com.example.cropdoc.activity_dashboard.domain.PlantingActivity
import com.example.cropdoc.activity_dashboard.domain.PlantingActivityDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider

class ActivityRepositoryImpl @Inject constructor(
    private val activityDaoProvider: Provider<PlantingActivityDao>,
    private val dailyActivityDaoProvider: Provider<DailyActivityDao>
):ActivityRepository {

    private val activityDao:PlantingActivityDao get() = activityDaoProvider.get()
    private val dailyActivityDao: DailyActivityDao get() = dailyActivityDaoProvider.get()
    override suspend fun addActivity(activity: PlantingActivity){
        activityDao.addActivity(activity)
    }

    override suspend fun getAllActivities(): Flow<List<PlantingActivity>> = activityDao.getAllActivities()

    override suspend fun getActivityById(id:Long) : Flow<PlantingActivity> {
        return activityDao.getActivityById(id)
    }

    override suspend fun updateActivity(activity: PlantingActivity){
        activityDao.updateActivity(activity)
    }

    override suspend fun deleteActivity(activity: PlantingActivity){
        activityDao.deleteActivity(activity)
    }

    override suspend fun addDailyActivity(dailyActivity: DailyActivity) {
        dailyActivityDao.addDailyActivity(dailyActivity)
    }

    override suspend fun getDailyActivitiesForPlantingActivity(plantingActivityId: Long): Flow<List<DailyActivity>> {
        return dailyActivityDao.getDailyActivitiesForPlantingActivity(plantingActivityId)
    }

    override suspend fun updateDailyActivity(dailyActivity: DailyActivity) {
        dailyActivityDao.updateDailyActivity(dailyActivity)
    }

    override suspend fun deleteDailyActivity(dailyActivity: DailyActivity) {
        dailyActivityDao.deleteDailyActivity(dailyActivity)
    }
}