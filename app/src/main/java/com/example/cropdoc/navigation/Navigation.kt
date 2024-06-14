package com.example.cropdoc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cropdoc.activity_dashboard.presentation.ActivityScreen
import com.example.cropdoc.activity_dashboard.presentation.DailyActivityScreen
import com.example.cropdoc.auth.presentation.AuthViewModel
import com.example.cropdoc.crop_suggestion.presentation.CropSuggestionIntro
import com.example.cropdoc.news.data.models.NewsArticle
import com.example.cropdoc.disease_prediction.presentation.DiseasePredictionScreen
import com.example.cropdoc.news.presentation.CategoryDetailScreen
import com.example.cropdoc.HomeScreen
import com.example.cropdoc.auth.presentation.LoginScreen
import com.example.cropdoc.news.presentation.NewsScreen
import com.example.cropdoc.auth.presentation.SignUpScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               authViewModel: AuthViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val isSignedIn = authViewModel.getCurrentUser() == null

    val startDestination = if (isSignedIn) Routes.HomeScreen.route else Routes.LoginScreen.route

    NavHost(navController = navController, startDestination = startDestination ) {

        composable(Routes.SignupScreen.route) {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Routes.LoginScreen.route) }
            ){
                navController.navigate(Routes.HomeScreen.route)
            }
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate(Routes.SignupScreen.route) }
            ) {
                navController.navigate(Routes.HomeScreen.route)
            }
        }

        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(route = Routes.NewsScreen.route){
            NewsScreen(navController, navigateToDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                navController.navigate(Routes.NewsDetailScreen.route)
            })
        }

        composable(route = Routes.NewsDetailScreen.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<NewsArticle>("cat")?: NewsArticle()
            CategoryDetailScreen(navController,category = category)
        }

        composable(route = Routes.CropRecommendation.route) {
            CropSuggestionIntro(navController)
        }

        composable(Routes.ActivityDashboardScreen.route){
            ActivityScreen(navController)
        }

        composable(Routes.DiseasePrediction.route){
            DiseasePredictionScreen(navController, context = context)
        }

        composable(Routes.DailyActivityScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ){entry->
            val id = if(entry.arguments != null)  entry.arguments!!.getLong("id") else 0L
            DailyActivityScreen(plantingActivityId = id, navController = navController)
        }
    }
}
