package com.example.cropdoc.ViewModels

import com.example.cropdoc.data.ActivityRepository
import com.example.cropdoc.data.PlantingActivity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PlantingActivityViewModel(
    private val activityRepository: ActivityRepository = Graph.activityRepository
): ViewModel() {

    var plantNameState by mutableStateOf("")
    var noOfPlantState by mutableStateOf("")
    var dateState by mutableStateOf("")
    var harvestWeeksState by mutableStateOf("")


    fun onplantNameChanged(newString:String){
        plantNameState = newString
    }

    fun onnoOfPlantChanged(newValue: String){
        noOfPlantState = newValue
    }

    fun ondateStateChanged(newDate: String){
        dateState = newDate
    }

    fun onharvestWeeksChanged(newValue: String){
        harvestWeeksState = newValue
    }

    lateinit var getAllActivity: Flow<List<PlantingActivity>>

    init {
        viewModelScope.launch {
            getAllActivity = activityRepository.getAllActivities()
        }
    }

    fun addActivity(activity:PlantingActivity){
        viewModelScope.launch(Dispatchers.IO) {
            activityRepository.addActivity(activity = activity)
        }
    }

    fun getActivityById(id:Long): Flow<PlantingActivity> {
        return activityRepository.getActivityById(id = id)
    }

    fun updateActivity(activity:PlantingActivity){
        viewModelScope.launch(Dispatchers.IO) {
            activityRepository.updateActivity(activity = activity)
        }
    }

    fun deleteActivity(activity:PlantingActivity){
        viewModelScope.launch(Dispatchers.IO) {
            activityRepository.deleteActivity(activity = activity)
            getAllActivity = activityRepository.getAllActivities()
        }
    }
}