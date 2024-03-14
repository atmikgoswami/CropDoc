package com.example.cropdoc

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class ActivityFeature:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}

//class ActivityFeature : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        FirebaseAuth.getInstance().currentUser?.let { user ->
//            val userEmail = user.email ?: ""
//            Graph.provide(this, userEmail)
//        }
//    }
//}