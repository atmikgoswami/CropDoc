package com.example.cropdoc.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Recommend
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val items = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Routes.HomeScreen.route
    ),
    NavigationItem(
        title = "Logout",
        selectedIcon = Icons.Filled.Logout,
        unselectedIcon = Icons.Outlined.Logout,
        route = Routes.LoginScreen.route
    ),
)

val items1 = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Routes.HomeScreen.route
    ),
    NavigationItem(
        title = "Detect",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = Routes.DiseasePrediction.route
    ),
    NavigationItem(
        title = "Suggest",
        selectedIcon = Icons.Filled.Recommend,
        unselectedIcon = Icons.Outlined.Recommend,
        route = Routes.CropRecommendation.route
    ),
    NavigationItem(
        title = "AgriNews",
        selectedIcon = Icons.Filled.Newspaper,
        unselectedIcon = Icons.Outlined.Newspaper,
        route = Routes.NewsScreen.route
    ),
    NavigationItem(
        title = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
        route = Routes.ActivityDashboardScreen.route
    ),
)