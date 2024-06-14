package com.example.cropdoc.di

import android.app.Application
import android.content.Context
import com.example.cropdoc.activity_dashboard.data.ActivityRepositoryImpl
import com.example.cropdoc.activity_dashboard.domain.ActivityRepository
import com.example.cropdoc.auth.data.repository.UserRepositoryImpl
import com.example.cropdoc.auth.domain.UserRepository
import com.example.cropdoc.crop_suggestion.data.repository.CropSuggestRepositoryImpl
import com.example.cropdoc.crop_suggestion.data.repository.WeatherRepositoryImpl
import com.example.cropdoc.crop_suggestion.domain.repository.CropSuggestRepository
import com.example.cropdoc.crop_suggestion.domain.repository.WeatherRepository
import com.example.cropdoc.disease_prediction.data.repository.DiseaseRepositoryImpl
import com.example.cropdoc.disease_prediction.domain.repository.DiseaseRepository
import com.example.cropdoc.news.data.repository.NewsRepositoryImpl
import com.example.cropdoc.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindCropSuggestRepository(
        cropSuggestRepositoryImpl: CropSuggestRepositoryImpl
    ): CropSuggestRepository

    @Binds
    @Singleton
    abstract fun bindDiseaseRepository(
        diseaseRepository: DiseaseRepositoryImpl
    ): DiseaseRepository

    @Binds
    @Singleton
    abstract fun bindActivityRepository(
        activityRepository: ActivityRepositoryImpl
    ): ActivityRepository
}