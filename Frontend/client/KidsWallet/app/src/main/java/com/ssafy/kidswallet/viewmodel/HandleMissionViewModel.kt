package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.HandleMissionModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class HandleMissionViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun rejectMission(begId: Int) {
        viewModelScope.launch {
            try {
                val handleMissionModel = HandleMissionModel(
                    begId = begId,
                    isAccept = false
                )
                Log.d("HandleMission", "Sending data to server - begId: ${handleMissionModel.begId}, isAccept: ${handleMissionModel.isAccept}")
                val response: Response<Unit> = RetrofitClient.apiService.handleMission(handleMissionModel)
                handleApiResponse(response)
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("HandleMissionError", "Network error: ${e.message}")
            }
        }
    }

    fun acceptMission(begId: Int) {
        viewModelScope.launch {
            try {
                val handleMissionModel = HandleMissionModel(
                    begId = begId,
                    isAccept = true
                )
                val response: Response<Unit> = RetrofitClient.apiService.handleMission(handleMissionModel)
                handleApiResponse(response)
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("HandleMissionError", "Network error: ${e.message}")
            }
        }
    }

    private fun handleApiResponse(response: Response<Unit>) {
        if (response.isSuccessful) {
            _apiResponseState.value = true
            Log.d("HandleMission", "Mission handled successfully")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            _errorState.value = "Failed to handle mission: $errorBody"
            Log.d("HandleMission", "Failed to handle mission: $errorBody")
        }
    }
}
