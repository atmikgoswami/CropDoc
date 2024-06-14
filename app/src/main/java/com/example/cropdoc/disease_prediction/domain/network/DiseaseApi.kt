package com.example.cropdoc.disease_prediction.domain.network

import com.example.cropdoc.disease_prediction.data.models.DiseasePredictionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import javax.inject.Singleton

@Singleton
interface DiseaseApi {
    @Multipart
    @POST("disease")
    suspend fun predictDisease(
        @Part crop : MultipartBody.Part
    ) : DiseasePredictionResponse
}