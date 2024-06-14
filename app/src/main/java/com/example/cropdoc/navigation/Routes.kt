package com.example.cropdoc.navigation

sealed class Routes(val route:String) {
    object HomeScreen: Routes("homescreen")
    object NewsScreen: Routes("newsscreen")
    object NewsDetailScreen: Routes("newsdetailscreen")
    object CropRecommendation : Routes("croprecommendation")
    object DiseasePrediction : Routes("diseaseprediction")
    object ActivityDashboardScreen : Routes("activity_dashboard_screen")
    object LoginScreen: Routes("loginscreen")
    object SignupScreen: Routes("signupscreen")
    object DailyActivityScreen : Routes("daily_activity_screen")
}