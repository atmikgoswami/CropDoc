package com.example.cropdoc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cropdoc.activity_dashboard.domain.PlantingActivity
import com.example.cropdoc.activity_dashboard.presentation.PlantingActivityViewModel
import com.example.cropdoc.navigation.Routes
import com.example.cropdoc.auth.presentation.AuthViewModel
import com.example.cropdoc.navigation.items
import kotlinx.coroutines.launch

data class ClickableItem(
    val title: String,
    val imageResourceId: Int,
    val route: String
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    viewModel: PlantingActivityViewModel = hiltViewModel(),
    ) {

    val clickables = listOf(
        ClickableItem("DISEASE IDENTIFICATION", R.drawable.img_3, Routes.DiseasePrediction.route),
        ClickableItem("CROP RECOMMENDATION", R.drawable.img_2, Routes.CropRecommendation.route),
        ClickableItem("AGRINEWS", R.drawable.img, Routes.NewsScreen.route),
        ClickableItem("YOUR CROPS", R.drawable.img_1, Routes.ActivityDashboardScreen.route)
    )

    viewModel.getAllActivities()
    val activities by viewModel.activities.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            if (item.title == "Logout") {
                                authViewModel.logout()
                                navController.navigate(item.route)
                            } else {
                                navController.navigate(item.route)
                            }
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },

                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(modifier = Modifier
                    .height(60.dp),
                    title = {
                        Box(
                            modifier = modifier.fillMaxSize(),
                        ) {
                            Text(
                                text = "CROPDOC",
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
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },modifier = modifier.padding(top=6.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                tint = Color.Black,
                                contentDescription = "Menu",

                            )
                        }
                    }
                )
            }
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp)
                        .padding(horizontal = 16.dp),
                ) {
                    item{
                        if(activities.isNotEmpty()){
                            Text(
                                text = "Your Crops \uD83C\uDF3E",
                                style = TextStyle(
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = modifier.padding(start = 15.dp)
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 8.dp, start = 5.dp)
                            ) {
                                items(activities) { activity ->
                                    HomeScreenActivities(activity){
                                        navController.navigate(Routes.DailyActivityScreen.route + "/${activity.id}")
                                    }
                                }
                            }
                        }
                    }
                    items(clickables) { clickableItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { navController.navigate(clickableItem.route) })
                                .padding(12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .border(1.dp,Color.Gray,RoundedCornerShape(20.dp))
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(clickableItem.imageResourceId),
                                contentDescription = null,
                                modifier = Modifier.size(75.dp).padding(8.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = clickableItem.title,
                                    color = Color.Black,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(modifier = modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenActivities(plantingActivity: PlantingActivity, navigateToActivity:(Long)->Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(20.dp))
            .clickable { navigateToActivity(plantingActivity.id) }
            .background(Color.White)
            .width(160.dp)
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = plantingActivity.date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CROP NAME : ${plantingActivity.plantName.uppercase()}",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "NO : ${plantingActivity.noOfPlants}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Weeks to harvest : ${plantingActivity.harvestWeeks}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

