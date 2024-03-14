package com.example.cropdoc

import android.content.Context
import androidx.room.Room
import com.example.cropdoc.data.ActivityDatabase
import com.example.cropdoc.data.ActivityRepository
import com.google.firebase.auth.FirebaseAuth

object Graph {
    lateinit var database: ActivityDatabase

    val activityRepository by lazy{
        ActivityRepository(activityDao = database.PlantingActivityDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, ActivityDatabase::class.java, "planting-activity.db").build()
    }

}

//object Graph {
//
//    lateinit var database: ActivityDatabase
//    val activityRepository by lazy {
//        ActivityRepository(database.PlantingActivityDao())
//    }
//
//    fun provide(context: Context, userEmail: String): ActivityDatabase {
//        val databaseName = generateDatabaseName(userEmail)
//        return Room.databaseBuilder(context, ActivityDatabase::class.java, databaseName).build()
//    }
//
//    private fun generateDatabaseName(userEmail: String): String {
//        return "planting-activity-${userEmail.replace(".", "_")}.db"
//    }
//}
