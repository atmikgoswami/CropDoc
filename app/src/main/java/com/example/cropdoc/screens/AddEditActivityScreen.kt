package com.example.cropdoc.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cropdoc.AppBarView
import com.example.cropdoc.R
import com.example.cropdoc.data.PlantingActivity
import com.example.cropdoc.ViewModels.PlantingActivityViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Add_Edit_activity_screen(
    id: Long,
    viewModel: PlantingActivityViewModel,
    navController: NavController
){
    
    val calendarState = rememberSheetState()
    
    CalendarDialog(state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date{
        date ->
            viewModel.dateState = date.toString()
            viewModel.ondateStateChanged(date.toString())
    })

    val snackMessage = remember{
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val activity = viewModel.getActivityById(id).collectAsState(initial = PlantingActivity(0L, "", "","",""))
        viewModel.plantNameState = activity.value.plantName
        viewModel.noOfPlantState = activity.value.noOfPlants
        viewModel.dateState = activity.value.date
        viewModel.harvestWeeksState = activity.value.harvestWeeks
    }else{
        viewModel.plantNameState = ""
        viewModel.noOfPlantState = ""
        viewModel.dateState = ""
        viewModel.harvestWeeksState = ""
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
            fontSize = 42.sp,
            stringResourceId =
                if(id != 0L) R.string.update_existing_activity_app_bar
                else R.string.add_new_activity_app_bar,
        ) {navController.navigateUp()}
        },
    ) {
        Column(modifier = Modifier
            .padding(it),
//            .wrapContentSize()
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(10.dp))

            ActivityTextField(label = "Plant Name",
                value = viewModel.plantNameState,
                onValueChanged = {
                    viewModel.onplantNameChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))

            ActivityTextField1(label = "No of Plants",
                value = viewModel.noOfPlantState,
                onValueChanged = {
                    viewModel.onnoOfPlantChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))

//            ActivityTextField(label = viewModel.dateState,
//                value = viewModel.dateState,
//                onValueChanged = {
//                    viewModel.ondateStateChanged(it)
//                } )

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(8.dp)
                .border(
                    BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(10)
                )
                .clickable {
                    calendarState.show()
                }, verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
            ){
                if(viewModel.dateState.isNotEmpty())
                    Text(text = viewModel.dateState,modifier = Modifier.padding(start = 15.dp))
                else
                    Text(text = "Date",modifier = Modifier.padding(start = 15.dp))

            }

            Spacer(modifier = Modifier.height(2.dp))

            ActivityTextField1(label = "Harvest duration in weeks",
                value = viewModel.harvestWeeksState,
                onValueChanged = {
                    viewModel.onharvestWeeksChanged(it)
                } )

            Spacer(modifier = Modifier.height(10.dp))


            Button(onClick={
                if(viewModel.plantNameState.isNotEmpty() &&
                    viewModel.noOfPlantState.isNotEmpty() &&
                    viewModel.dateState.isNotEmpty() &&
                    viewModel.harvestWeeksState.isNotEmpty()){
                    if(id != 0L){
                        viewModel.updateActivity(
                            PlantingActivity(
                                id = id,
                                plantName = viewModel.plantNameState.trim(),
                                noOfPlants = viewModel.noOfPlantState.trim(),
                                date = viewModel.dateState.trim(),
                                harvestWeeks = viewModel.harvestWeeksState.trim()
                            )
                        )
                    }else{
                        //  AddWish
                        viewModel.addActivity(
                            PlantingActivity(
                                plantName = viewModel.plantNameState.trim(),
                                noOfPlants = viewModel.noOfPlantState.trim(),
                                date = viewModel.dateState.trim(),
                                harvestWeeks = viewModel.harvestWeeksState.trim()
                            )
                        )
                        snackMessage.value = "Activity has been created"
                    }
                }else{
                    //
                    snackMessage.value = "Enter fields to create an activity"
                }
                scope.launch {
                    //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }

            }){
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_existing_activity_app_bar)
                    else stringResource(
                        id = R.string.add_new_activity_app_bar
                    ),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}


@Composable
fun ActivityTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // using predefined Color
            textColor = Color.Black,
            // using our own colors in Res.Values.Color
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )


    )
}

@Composable
fun ActivityTextField1(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // using predefined Color
            textColor = Color.Black,
            // using our own colors in Res.Values.Color
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}

