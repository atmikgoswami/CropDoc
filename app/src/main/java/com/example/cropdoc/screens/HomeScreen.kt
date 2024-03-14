package com.example.cropdoc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cropdoc.R
import com.example.cropdoc.Screen
import com.example.cropdoc.ViewModels.AuthViewModel
import com.example.cropdoc.data.items
import com.example.cropdoc.data.items1
import kotlinx.coroutines.launch

data class ClickableItem(
    val title: String,
    val imageResourceId: Int,
    val route: String
)

@Composable
fun HomeScreen(navController: NavController,
               authViewModel: AuthViewModel,) {

    val clickables = listOf(
        ClickableItem("DISEASE IDENTIFICATION", R.drawable.img_3, Screen.DiseasePrediction.route),
        ClickableItem("CROP RECOMMENDATION", R.drawable.img_2, Screen.CropRecommendation.route),
        ClickableItem("AGRICULTURE NEWS", R.drawable.img, Screen.NewsScreen.route),
        ClickableItem("ACTIVITY DASHBOARD", R.drawable.img_1, Screen.ActivityDashboardScreen.route)
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var selectedItemIndex1 by rememberSaveable {
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
                    .height(90.dp),
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.home_screen_app_bar),
                                color = colorResource(id = R.color.white),
                                modifier = Modifier
//                            .padding(start = 2.dp)
                                    .heightIn(max = 48.dp)
                                    .align(Alignment.Center),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 45.sp
                                ),
                            )
                        }
                    },
                    elevation = 10.dp,
                    backgroundColor = colorResource(id = R.color.customgreen),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                tint = Color.White,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
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
            }
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(it)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Welcome !",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 25.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(clickables) { clickableItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)
                                    .clickable(onClick = { navController.navigate(clickableItem.route) })
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Image on the left
                                Spacer(modifier = Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(clickableItem.imageResourceId), // Replace with your image resource ID
                                    contentDescription = null, // Provide content description for accessibility
                                    modifier = Modifier.size(96.dp) // Adjust size as needed
                                )

                                // Spacer to separate image and text
                                Spacer(modifier = Modifier.width(16.dp))

                                // Text on the right
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(), // Take remaining space
                                    contentAlignment = Alignment.CenterStart // Align text to start
                                ) {
                                    Text(
                                        text = clickableItem.title,
                                        color = Color.Black,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 25.sp
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
