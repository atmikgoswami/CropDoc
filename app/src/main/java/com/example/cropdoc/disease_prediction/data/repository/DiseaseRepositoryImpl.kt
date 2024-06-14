package com.example.cropdoc.disease_prediction.data.repository

import com.example.cropdoc.disease_prediction.data.models.DiseasePredictionResponse
import com.example.cropdoc.disease_prediction.domain.network.DiseaseApi
import com.example.cropdoc.disease_prediction.domain.repository.DiseaseRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class DiseaseRepositoryImpl @Inject constructor(
    private val api:DiseaseApi):DiseaseRepository {

    override suspend fun getDiseasePrediction(part: MultipartBody.Part): DiseasePredictionResponse {
        return api.predictDisease(part)
    }
}