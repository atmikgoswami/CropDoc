package com.example.cropdoc.auth.presentation

import android.util.Log
import com.example.cropdoc.auth.domain.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.auth.data.models.User
import com.example.cropdoc.auth.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.Lazy

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Lazy<UserRepository>
) : ViewModel() {

    init{
        repository.get()
    }
    private val _loginResult = MutableStateFlow<Result<Boolean>?>(null)
    val loginResult: StateFlow<Result<Boolean>?> get() = _loginResult

    private val _logoutResult = MutableStateFlow<Result<Boolean>?>(null)
    val logoutResult: StateFlow<Result<Boolean>?> get() = _logoutResult

    private val _signupResult = MutableStateFlow<Result<Boolean>?>(null)
    val signupResult: StateFlow<Result<Boolean>?> get() = _signupResult


    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            repository.get().signUp(email, password, firstName, lastName).collect{
                _signupResult.value = it
            }
        }
    }

    fun getCurrentUser():Result<User>? {
        var user:Result<User>? = null
        viewModelScope.launch {
            repository.get().getCurrentUser().collect{
                user = it
            }
        }
        return user
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.get().login(email, password).collect{
                _loginResult.value = it
                Log.d("sign in","authResult changed to $it")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.get().logout().collect{
                _logoutResult.value = it
            }
        }
    }
}

