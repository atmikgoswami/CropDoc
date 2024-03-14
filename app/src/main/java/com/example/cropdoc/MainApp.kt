package com.example.cropdoc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cropdoc.ViewModels.AuthViewModel
import com.example.cropdoc.ViewModels.NewsMainViewModel
import com.example.cropdoc.ViewModels.PlantingActivityViewModel
import com.example.cropdoc.data.NewsArticle
import com.example.cropdoc.data.Result
import com.example.cropdoc.data.UserRepository
import com.example.cropdoc.screens.ActivityScreen
import com.example.cropdoc.screens.Add_Edit_activity_screen
import com.example.cropdoc.screens.CategoryDetailScreen
import com.example.cropdoc.screens.CropSuggestionIntro
import com.example.cropdoc.screens.DiseasePredictionScreen
import com.example.cropdoc.screens.HomeScreen
import com.example.cropdoc.screens.LoginScreen
import com.example.cropdoc.screens.NewsScreen
import com.example.cropdoc.screens.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(navController: NavHostController = rememberNavController()){
    val newsViewModel: NewsMainViewModel = viewModel()
    val activityViewModel : PlantingActivityViewModel = viewModel()
    val viewState by newsViewModel.categoriesState
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    val userRepository = UserRepository(
        FirebaseAuth.getInstance(),
        Injection.instance()
    )

    LaunchedEffect(key1 = true) {
        val result = userRepository.getCurrentUser()
        when (result) {
            is com.example.cropdoc.data.Result.Success -> {
                // User is already logged in, navigate to the home screen
                navController.navigate(Screen.HomeScreenFixedBar.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
            is Result.Error -> {
                // User is not logged in, navigate to the signup screen
                navController.navigate(Screen.SignupScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
            else -> {
                // Handle any other unexpected cases here
            }
        }

    }

    NavHost(navController = navController, startDestination = Screen.HomeScreenFixedBar.route ) {


        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            ){
                navController.navigate(Screen.HomeScreenFixedBar.route)
            }
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
            ) {
                navController.navigate(Screen.HomeScreenFixedBar.route)
            }
        }


        composable(route = Screen.HomeScreenFixedBar.route) {
            HomeScreen(navController, authViewModel)
        }

        composable(route = Screen.NewsScreen.route){
            NewsScreen(navController,viewState = viewState, navigateToDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                navController.navigate(Screen.NewsDetailScreen.route)
            })
        }

        composable(route = Screen.NewsDetailScreen.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<NewsArticle>("cat")?: NewsArticle(null,"","","","","","","")
            CategoryDetailScreen(navController,category = category)
        }

        composable(route = Screen.CropRecommendation.route) {
            CropSuggestionIntro(navController,weatherViewModel = viewModel(), cropViewModel = viewModel())
        }

        composable(Screen.ActivityDashboardScreen.route){
            ActivityScreen(navController, context = context, viewModel = activityViewModel)
        }

        composable(Screen.DiseasePrediction.route){
            DiseasePredictionScreen(navController, context = context, diseasePredictionViewModel = viewModel())
        }

        composable(Screen.Add_Edit_activity_screen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ){entry->
            val id = if(entry.arguments != null)  entry.arguments!!.getLong("id") else 0L
            Add_Edit_activity_screen(id = id, viewModel = viewModel() , navController = navController)
        }

    }
}
