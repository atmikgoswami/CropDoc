package com.example.cropdoc.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cropdoc.AppBarView
import com.example.cropdoc.R
import com.example.cropdoc.Screen
import com.example.cropdoc.data.PlantingActivity
import com.example.cropdoc.ViewModels.PlantingActivityViewModel
import com.example.cropdoc.data.items1

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityScreen(
    navController: NavController,
    context: Context,
    modifier: Modifier = Modifier,
    viewModel : PlantingActivityViewModel
){
    val scaffoldState = rememberScaffoldState()

    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                fontSize = 38.sp,
                stringResourceId = R.string.activity_dashboard_screen_app_bar,
            ) {navController.navigateUp()}
        },
        bottomBar = {
            NavigationBar {
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
                            androidx.compose.material3.Icon(
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
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
//                    Toast.makeText(context, "FAButton Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.Add_Edit_activity_screen.route + "/0L")

                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        })
    {
        Box(modifier = modifier.fillMaxSize().padding(it)) {

            val activityList = viewModel.getAllActivity.collectAsState(initial = listOf())

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(it)){
                items(activityList.value, key={activity-> activity.id} ){
                        activity ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if(it == DismissValue.DismissedToEnd || it== DismissValue.DismissedToStart){
                                viewModel.deleteActivity(activity)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if(dismissState.dismissDirection
                                    == DismissDirection.EndToStart) Color.Red else Color.Transparent
                                ,label = ""
                            )
                            val alignment = Alignment.CenterEnd
                            Box(
                                Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ){
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White)
                            }

                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(1f) },
                        dismissContent = {
                            ActivityItem(activity = activity) {
                                val id = activity.id
                                navController.navigate(Screen.Add_Edit_activity_screen.route + "/$id")
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
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = "PLANT NAME : ${activity.plantName.uppercase()}", fontWeight = FontWeight.ExtraBold)
            Text(text = "NO : ${activity.noOfPlants}")
            Text(text = "Planting Date : ${activity.date}")
            Text(text = "Weeks to harvest : ${activity.harvestWeeks}")
        }
    }
}