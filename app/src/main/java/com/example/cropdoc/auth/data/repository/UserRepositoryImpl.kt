package com.example.cropdoc.auth.data.repository

import android.util.Log
import com.example.cropdoc.auth.data.models.User
import com.example.cropdoc.auth.domain.UserRepository
import com.example.cropdoc.auth.domain.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):UserRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Flow<Result<Boolean>> = callbackFlow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(firstName, lastName, email)
            saveUserToFirestore(user)
            login(email, password)
            trySend(Result.Success(true))
        } catch (e: Exception) {
            trySend(Result.Error(e))
        }
        awaitClose()
    }

    override suspend fun login(email: String, password: String): Flow<Result<Boolean>> = callbackFlow {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d("sign in","success")
                trySend(Result.Success(true))
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }
        awaitClose()
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    override suspend fun getCurrentUser(): Flow<Result<User>> = callbackFlow {
        try {
            val uid = auth.currentUser?.email
            if (uid != null) {
                val userDocument = firestore.collection("users").document(uid).get().await()
                val user = userDocument.toObject(User::class.java)
                if (user != null) {
                    Log.d("user2", "$uid")
                    trySend(Result.Success(user))
                } else {
                    trySend(Result.Error(Exception("User data not found")))
                }
            } else {
                trySend(Result.Error(Exception("User not authenticated")))
            }
        } catch (e: Exception) {
            trySend(Result.Error(e))
        }
        awaitClose()
    }

    override suspend fun logout(): Flow<Result<Boolean>> = callbackFlow {
        try {
            auth.signOut()
            trySend(Result.Success(true))
        } catch (e: Exception) {
            trySend(Result.Error(e))
        }
        awaitClose()
    }
}
