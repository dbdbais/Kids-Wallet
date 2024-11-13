package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.GiveMissionModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class GiveMissionViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun sendMission(begId: Int, missionMessage: String) {
        viewModelScope.launch {
            try {
                val requestBody = GiveMissionModel(
                    begId = begId,
                    missionMessage = missionMessage
                )
                Log.d("SendMission", "Sending mission data - begId: ${requestBody.begId}, missionMessage: ${requestBody.missionMessage}")

                val response: Response<Unit> = RetrofitClient.apiService.giveMission(requestBody)
                handleApiResponse(response)
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("GiveMissionError", "Network error: ${e.message}")
            }
        }
    }

    private fun handleApiResponse(response: Response<Unit>) {
        if (response.isSuccessful) {
            _apiResponseState.value = true
            Log.d("GiveMission", "Mission sent successfully")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            _errorState.value = "Failed to send mission: $errorBody"
            Log.d("GiveMission", "Failed to send mission: $errorBody")
        }
    }
}