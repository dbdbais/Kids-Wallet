package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.BegDto
import com.ssafy.kidswallet.data.model.Mission
import com.ssafy.kidswallet.data.model.MissionModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeggingMissionViewModel : ViewModel() {
    private val _missionList = MutableStateFlow<List<MissionModel>>(emptyList())
    val missionList: StateFlow<List<MissionModel>> = _missionList

    private var currentStart = 1
    private val itemsPerPage = 20

    fun fetchMissionList(userId: Int, reset: Boolean = false) {
        if (reset) {
            currentStart = 1
            _missionList.value = emptyList()
        }
        viewModelScope.launch {
            try {
                val response = apiService.beggingMissionList(userId, currentStart, currentStart + itemsPerPage - 1)
                if (response.isSuccessful) {
                    val newMissions = response.body()?.data ?: emptyList()
                    _missionList.value = if (currentStart == 1) {
                        newMissions
                    } else {
                        _missionList.value + newMissions
                    }

                    currentStart += itemsPerPage
                } else {
                    Log.e("BeggingMissionViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("BeggingMissionViewModel", "Network error: ${e.message}")
            }
        }
    }
}