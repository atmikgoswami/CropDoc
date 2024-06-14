package com.example.cropdoc.auth.domain

import com.example.cropdoc.auth.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): Flow<Result<Boolean>>
    suspend fun login(email: String, password: String): Flow<Result<Boolean>>
    suspend fun getCurrentUser(): Flow<Result<User>>
    suspend fun logout(): Flow<Result<Boolean>>
}