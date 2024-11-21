package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MakeCardViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun registerCard(userId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.registerCard(userId)
                if (response.isSuccessful && response.code() == 200) {
                    _apiResponseState.value = true
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to register Card: $errorBody"
                    Log.d("CardRegister", "Failed to register card: $errorBody")
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("CardRegisterError", "Network error: ${e.message}")
            }
        }
    }
}