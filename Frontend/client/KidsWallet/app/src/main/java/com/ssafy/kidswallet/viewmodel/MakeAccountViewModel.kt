package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MakeAccountViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun registerAccount(userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.registerAccount(userId)
                if (response.isSuccessful) {
                    _apiResponseState.value = true
                    Log.d("AccountRegister", "Account registered successfully")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to register account: $errorBody"
                    Log.d("AccountRegister", "Failed to register account: $errorBody")
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("AccountRegisterError", "Network error: ${e.message}")
            }
        }
    }
}