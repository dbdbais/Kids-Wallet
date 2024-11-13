package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.PlayMissionModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class PlayMissionViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun sendMission(missionId: Int, base64Image: String) {
        viewModelScope.launch {
            try {
                val playMissionModel = PlayMissionModel(
                    missionId = missionId,
                    base64Image = base64Image
                )

                Log.d("PlayMissionViewModel", "Sending mission: missionId=$missionId, base64Image=${base64Image.take(100)}...") // Limit base64 log length for readability

                val response: Response<Unit> = RetrofitClient.apiService.playMission(playMissionModel)

                if (response.isSuccessful) {
                    _apiResponseState.value = true
                    Log.d("PlayMission", "Mission completed successfully")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to complete mission: $errorBody"
                    Log.d("PlayMission", "Failed to complete mission: $errorBody")
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("PlayMissionError", "Network error: ${e.message}")
            }
        }
    }
}