package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherDetailModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TogetherDetailViewModel.kt

class TogetherDetailViewModel : ViewModel() {
    private val _togetherDetail = MutableStateFlow<TogetherDetailModel?>(null)
    val togetherDetail: StateFlow<TogetherDetailModel?> = _togetherDetail

    fun fetchTogetherDetail(togetherRunId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.togetherDetail(togetherRunId)
                if (response.isSuccessful) {
                    _togetherDetail.value = response.body()?.data
                } else {
                    Log.e("TogetherDetailViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TogetherDetailViewModel", "Network error: ${e.message}")
            }
        }
    }

    fun deleteTogetherRun(savingContractId: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteTogetherRunSavings(savingContractId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("삭제 실패: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                onFailure("삭제 중 네트워크 오류: ${e.message}")
            }
        }
    }
}

