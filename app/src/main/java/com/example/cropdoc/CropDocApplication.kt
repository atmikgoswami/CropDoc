package com.example.cropdoc

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CropDocApplication : Application(){
    init {
        instance = this
    }

    companion object {
        private var instance: CropDocApplication? = null

        val applicationContext: Context
            get() {
                return instance!!.applicationContext
            }
    }
}