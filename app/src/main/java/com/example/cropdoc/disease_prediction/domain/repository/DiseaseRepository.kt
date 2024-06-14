package com.example.cropdoc.disease_prediction.domain.repository

import com.example.cropdoc.disease_prediction.data.models.DiseasePredictionResponse
import okhttp3.MultipartBody

interface DiseaseRepository {
    suspend fun getDiseasePrediction(part : MultipartBody.Part): DiseasePredictionResponse
}