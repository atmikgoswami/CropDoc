package com.example.cropdoc.activity_dashboard.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cropdoc.R
import com.example.cropdoc.activity_dashboard.domain.PlantingActivity
import com.example.cropdoc.navigation.Routes
import com.example.cropdoc.navigation.items1
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel : PlantingActivityViewModel = hiltViewModel()
){
    viewModel.getAllActivities()
    val activities by viewModel.activities.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    val calendarState = rememberSheetState()

    val snackMessage = remember{
        mutableStateOf("")
    }

    CalendarDialog(state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date{
                date ->
            viewModel.dateState = date.toString()
            viewModel.onDateStateChanged(date.toString())
        })

    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(4)
    }
    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .height(60.dp),
                title = {
                    Box(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Your Crops",
                            color = colorResource(id = R.color.black),
                            modifier = modifier
                                .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
                                .align(Alignment.CenterStart),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                        )
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "back",
                            modifier = modifier
                                .size(38.dp)
                                .padding(top = 12.dp)

                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
                items1.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex1 == index,
                        onClick = {
                            selectedItemIndex1 = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex1) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    showDialog = true
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        })
    {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(it))
        {
            if(showDialog){
                AlertDialog(onDismissRequest = { showDialog = true },
                    title = {
                        Row {
                            Text("New Crop Planting",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = modifier.padding(top = 8.dp,start = 6.dp))
                            Spacer(modifier = modifier.width(8.dp))
                            IconButton(onClick = { showDialog = false }) {
                                Icon(painter = rememberVectorPainter(Icons.Default.Close), contentDescription = "close dialog")
                            }
                        }
                    },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = viewModel.plantNameState,
                                onValueChange = { plantName -> viewModel.onPlantNameChanged(plantName) },
                                singleLine = true,
                                label = { Text(text = "Plant Name")},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            )
                            OutlinedTextField(
                                value = viewModel.noOfPlantState,
                                onValueChange = { no-> viewModel.onNoOfPlantsChanged(no) },
                                singleLine = true,
                                label = { Text(text = "No of plants")},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(8.dp)
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                    shape = RoundedCornerShape(10)
                                )
                                .clickable {
                                    calendarState.show()
                                }, verticalAlignment = Alignment.CenterVertically,
                            ){
                                if(viewModel.dateState.isNotEmpty())
                                    Text(text = viewModel.dateState,modifier = Modifier.padding(start = 15.dp))
                                else
                                    Text(text = "Date",modifier = Modifier.padding(start = 15.dp))

                            }
                            OutlinedTextField(
                                value = viewModel.harvestWeeksState,
                                onValueChange = { weeks-> viewModel.onHarvestWeeksChanged(weeks) },
                                singleLine = true,
                                label = { Text(text = "Harvest in weeks")},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }

                    }, confirmButton = {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    if (viewModel.plantNameState.isNotEmpty() &&
                                        viewModel.noOfPlantState.isNotEmpty() &&
                                        viewModel.dateState.isNotEmpty() &&
                                        viewModel.harvestWeeksState.isNotEmpty()
                                    )
                                    {
                                        showDialog = false
                                        viewModel.addActivity(
                                            PlantingActivity(
                                                plantName = viewModel.plantNameState.trim(),
                                                noOfPlants = viewModel.noOfPlantState.trim(),
                                                date = viewModel.dateState.trim(),
                                                harvestWeeks = viewModel.harvestWeeksState.trim()
                                            )
                                        )
                                        viewModel.getAllActivities()
                                        viewModel.plantNameState = ""
                                        viewModel.noOfPlantState = ""
                                        viewModel.dateState = ""
                                        viewModel.harvestWeeksState = ""
                                        snackMessage.value = "Activity has been created"
                                    } else {
                                        snackMessage.value = "Enter fields to create an activity"
                                    }
                                }
                            ) {
                                Text("Add")
                            }
                        }
                    },
                )
            }
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp))
            {
                items(activities, key={activity-> activity.id} ){
                        activity ->

                    val swipeToDismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {dismissValue->
                            if(dismissValue == SwipeToDismissBoxValue.EndToStart){
                                viewModel.deleteActivity(activity)
                            }
                            true
                        },
                        positionalThreshold = { 1f }
                    )
                    SwipeToDismissBox(
                        state = swipeToDismissState,
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val color by animateColorAsState(
                                if (swipeToDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.Transparent,
                                label = "Swipe To Dismiss Color Animation"
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .clip(RoundedCornerShape(10))
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White
                                )
                            }
                        },
                        content = {
                            ActivityItem(activity = activity) {
                                val id = activity.id
                                navController.navigate(Routes.DailyActivityScreen.route + "/$id")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityItem(activity: PlantingActivity, onClick: () -> Unit){

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = "CROP NAME : ${activity.plantName.uppercase()}", fontWeight = FontWeight.ExtraBold)
            Text(text = "NO : ${activity.noOfPlants}")
            Text(text = "Planting Date : ${activity.date}")
            Text(text = "Weeks to harvest : ${activity.harvestWeeks}")
        }
    }
}

