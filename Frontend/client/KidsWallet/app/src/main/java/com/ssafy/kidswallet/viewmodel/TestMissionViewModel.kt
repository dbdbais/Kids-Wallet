package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TestMissionModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class TestMissionViewModel : ViewModel() {

    private val _apiResponseState = MutableStateFlow<Boolean?>(null)
    val apiResponseState: StateFlow<Boolean?> = _apiResponseState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun testMission(missionId: Int, isComplete: Boolean) {
        viewModelScope.launch {
            try {
                val testMissionModel = TestMissionModel(
                    missionId = missionId,
                    isComplete = isComplete
                )

                Log.d("TestMissionViewModel", "Sending data to API: missionId = $missionId, isComplete = $isComplete")

                val response: Response<Unit> = RetrofitClient.apiService.testMission(testMissionModel)

                if (response.isSuccessful) {
                    _apiResponseState.value = true
                    Log.d("TestMission", "Mission status changed successfully")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to change mission status: $errorBody"
                    Log.d("TestMission", "Failed to change mission status: $errorBody")
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _apiResponseState.value = false
                Log.d("TestMissionError", "Network error: ${e.message}")
            }
        }
    }
}