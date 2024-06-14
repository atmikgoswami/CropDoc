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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cropdoc.activity_dashboard.domain.DailyActivity
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyActivityScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    plantingActivityId: Long,
    viewModel: PlantingActivityViewModel = hiltViewModel(),
) {
    viewModel.getDailyActivitiesForPlantingActivity(plantingActivityId)
    val dailyActivities by viewModel.dailyActivities.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var update by remember { mutableStateOf(false) }
    var activityid by remember { mutableStateOf(0L)}
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
            minYear = LocalDate.now().year,
        ),
        selection = CalendarSelection.Date { date ->
            viewModel.dailyActivityDateState = date.toString()
            viewModel.onDailyActivityDateChanged(date.toString())
        },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.height(60.dp),
                title = {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            text = "Daily Activities",
                            color = Color.Black,
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
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(it)) {

            if(showDialog){
                AlertDialog(onDismissRequest = { showDialog = true },
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text(if (update) "Update activity" else "Record new activity",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = modifier.padding(top = 10.dp).weight(1f))
                            IconButton(onClick = { showDialog = false; update = false}, modifier = modifier.padding(top=0.dp)) {
                                Icon(painter = rememberVectorPainter(Icons.Default.Close), contentDescription = "close dialog")
                            }
                        }
                    },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = viewModel.dailyActivityTitleState,
                                onValueChange = { title-> viewModel.onDailyActivityTitleChanged(title) },
                                singleLine = true,
                                label = { Text(text = "Title")},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            )

                            OutlinedTextField(
                                value = viewModel.dailyActivityDescriptionState,
                                onValueChange = { desc-> viewModel.onDailyActivityDescriptionChanged(desc)},
                                singleLine = false,
                                label = { Text(text = "Description")},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .height(90.dp)
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
                                //                horizontalArrangement = Arrangement.Center
                            ){
                                if(viewModel.dailyActivityDateState.isNotEmpty())
                                    Text(text = viewModel.dailyActivityDateState,modifier = Modifier.padding(start = 15.dp))
                                else
                                    Text(text = "Date",modifier = Modifier.padding(start = 15.dp))

                            }
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
                                    if (viewModel.dailyActivityTitleState.isNotBlank()
                                        && viewModel.dailyActivityDescriptionState.isNotBlank()
                                        && viewModel.dailyActivityDateState.isNotBlank()
                                        ) {
                                        showDialog = false
                                        if(update){
                                            viewModel.updateDailyActivity(
                                                DailyActivity(
                                                    id = activityid,
                                                    plantingActivityId = plantingActivityId,
                                                    title = viewModel.dailyActivityTitleState.trim(),
                                                    description = viewModel.dailyActivityDescriptionState.trim(),
                                                    date = viewModel.dailyActivityDateState.trim()
                                                )
                                            )
                                        }
                                        else{
                                            viewModel.addDailyActivity(
                                                DailyActivity(
                                                    plantingActivityId = plantingActivityId,
                                                    title = viewModel.dailyActivityTitleState.trim(),
                                                    description = viewModel.dailyActivityDescriptionState.trim(),
                                                    date = viewModel.dailyActivityDateState.trim()
                                                )
                                            )
                                        }
                                        activityid = 0L
                                        update = false
                                        viewModel.getDailyActivitiesForPlantingActivity(plantingActivityId)
                                        viewModel.onDailyActivityTitleChanged("")
                                        viewModel.onDailyActivityDescriptionChanged("")
                                        viewModel.onDailyActivityDateChanged("")
                                    }
                                }
                            ) {
                                Text(text = if(update) "Update" else "Add")
                            }
                        }
                    },
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                items(dailyActivities, key = { dailyActivity -> dailyActivity.id }) { dailyActivity ->
                    val swipeToDismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {dismissValue->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.deleteDailyActivity(dailyActivity)
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
                            DailyActivityItem(dailyActivity = dailyActivity) {
                                showDialog = true
                                update = true
                                activityid = dailyActivity.id
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DailyActivityItem(dailyActivity: DailyActivity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "TITLE : ${dailyActivity.title}", fontWeight = FontWeight.ExtraBold)
                Text(text = "DESCRIPTION : ${dailyActivity.description}")
                Text(text = "Date : ${dailyActivity.date}")
            }
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

