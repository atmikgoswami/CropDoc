package com.example.cropdoc.di

import android.content.Context
import androidx.room.Room
import com.example.cropdoc.activity_dashboard.data.PlantingActivityDatabase
import com.example.cropdoc.activity_dashboard.domain.DailyActivityDao
import com.example.cropdoc.activity_dashboard.domain.PlantingActivityDao
import com.example.cropdoc.crop_suggestion.domain.network.CropSuggestApi
import com.example.cropdoc.crop_suggestion.domain.network.WeatherApi
import com.example.cropdoc.disease_prediction.domain.network.DiseaseApi
import com.example.cropdoc.news.domain.network.NewsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Increase the timeout duration
            .readTimeout(30, TimeUnit.SECONDS)    // Increase the timeout duration
            .writeTimeout(30, TimeUnit.SECONDS)   // Increase the timeout duration
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCropSuggestApi(okHttpClient: OkHttpClient):CropSuggestApi {
        return Retrofit.Builder()
            .baseUrl("https://cropdoc-backend.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CropSuggestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDiseaseApi(okHttpClient: OkHttpClient):DiseaseApi{
        return Retrofit.Builder()
            .baseUrl("https://cropdoc-backend.onrender.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiseaseApi::class.java)
    }

    @Provides
    fun provideUserId(auth: FirebaseAuth): String {
        return auth.currentUser?.uid ?: ""
    }

    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        userIdProvider: Provider<String>
    ): PlantingActivityDatabase {
        val userId = userIdProvider.get()
        return Room.databaseBuilder(appContext, PlantingActivityDatabase::class.java, "planting_activity_database_$userId.db").build()
    }

    @Provides
    fun providePlantingActivityDao(db: PlantingActivityDatabase): PlantingActivityDao {
        return db.PlantingActivityDao()
    }

    @Provides
    fun provideDailyActivityDao(db: PlantingActivityDatabase): DailyActivityDao {
        return db.dailyActivityDao()
    }
}