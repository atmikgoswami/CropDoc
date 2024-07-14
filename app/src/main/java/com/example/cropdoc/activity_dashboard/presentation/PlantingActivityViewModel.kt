package com.example.cropdoc.activity_dashboard.presentation

import com.example.cropdoc.activity_dashboard.domain.PlantingActivity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.activity_dashboard.domain.ActivityRepository
import com.example.cropdoc.activity_dashboard.domain.DailyActivity
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PlantingActivityViewModel @Inject constructor(
    private val repository: Lazy<ActivityRepository>
): ViewModel() {

    var plantNameState by mutableStateOf("")
    var noOfPlantState by mutableStateOf("")
    var dateState by mutableStateOf("")
    var harvestWeeksState by mutableStateOf("")

    private val _activities = MutableStateFlow<List<PlantingActivity>>(emptyList())
    val activities: StateFlow<List<PlantingActivity>> get() = _activities

    private val _activity = MutableStateFlow<PlantingActivity?>(null)
    val activity: StateFlow<PlantingActivity?> get() = _activity

    private val _dailyActivities = MutableStateFlow<List<DailyActivity>>(emptyList())
    val dailyActivities: StateFlow<List<DailyActivity>> get() = _dailyActivities

    var dailyActivityTitleState by mutableStateOf("")
    var dailyActivityDescriptionState by mutableStateOf("")
    var dailyActivityDateState by mutableStateOf("")


    init {
        repository.get()
        getAllActivities()
    }
    fun onPlantNameChanged(newString:String){
        plantNameState = newString
    }

    fun onNoOfPlantsChanged(newValue: String){
        noOfPlantState = newValue
    }

    fun onDateStateChanged(newDate: String){
        dateState = newDate
    }

    fun onHarvestWeeksChanged(newValue: String){
        harvestWeeksState = newValue
    }

    fun onDailyActivityTitleChanged(newDescription: String) {
        dailyActivityTitleState = newDescription
    }

    fun onDailyActivityDescriptionChanged(newDescription: String) {
        dailyActivityDescriptionState = newDescription
    }

    fun onDailyActivityDateChanged(newDate: String) {
        dailyActivityDateState = newDate
    }

    fun addActivity(activity:PlantingActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().addActivity(activity = activity)
            getAllActivities()
        }
    }

    fun getActivityById(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().getActivityById(id = id).collect{
                _activity.value = it
            }
        }
    }

    fun updateActivity(activity:PlantingActivity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().updateActivity(activity = activity)
        }
    }

    fun getAllActivities(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().getAllActivities().collect{
                _activities.value = it
            }
        }
    }

    fun deleteActivity(activity:PlantingActivity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().deleteActivity(activity = activity)
            getAllActivities()
        }
    }

    fun addDailyActivity(dailyActivity: DailyActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().addDailyActivity(dailyActivity)
            getDailyActivitiesForPlantingActivity(dailyActivity.plantingActivityId)
        }
    }

    fun getDailyActivitiesForPlantingActivity(plantingActivityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().getDailyActivitiesForPlantingActivity(plantingActivityId).collect {
                _dailyActivities.value = it
            }
        }
    }

    fun updateDailyActivity(dailyActivity: DailyActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().updateDailyActivity(dailyActivity)
            getDailyActivitiesForPlantingActivity(dailyActivity.plantingActivityId)
        }
    }

    fun deleteDailyActivity(dailyActivity: DailyActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().deleteDailyActivity(dailyActivity)
            getDailyActivitiesForPlantingActivity(dailyActivity.plantingActivityId)
        }
    }
}