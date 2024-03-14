package com.example.cropdoc

sealed class Screen(val route:String) {
    object HomeScreenFixedBar:Screen("homescreen")
    object NewsScreen:Screen("newsscreen")
    object NewsDetailScreen:Screen("newsdetailscreen")
    object CropRecommendation : Screen("croprecommendation")
    object DiseasePrediction : Screen("diseaseprediction")
    object ActivityDashboardScreen : Screen("activity_dashboard_screen")
    object Add_Edit_activity_screen : Screen("add_edit_activity_screen")
    object LoginScreen:Screen("loginscreen")
    object SignupScreen:Screen("signupscreen")
}