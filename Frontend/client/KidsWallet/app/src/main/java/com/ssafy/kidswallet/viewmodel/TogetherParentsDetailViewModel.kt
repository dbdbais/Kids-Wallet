package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherParentsDetailModel
import com.ssafy.kidswallet.data.model.TogetherDetailResponse
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TogetherParentsDetailViewModel : ViewModel() {
    private val _togetherDetailResponse = MutableStateFlow<TogetherDetailResponse?>(null)

    fun acceptTogetherRun(
        togetherRunId: Int,
        userId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val requestBody = TogetherParentsDetailModel(userId)
                val response = apiService.togetherParentsDetail(togetherRunId, requestBody)

                if (response.isSuccessful) {
                    _togetherDetailResponse.value = response.body()
                    onSuccess()
                } else {
                    Log.e("TogetherParentsDetailViewModel", "요청 실패: 코드 = ${response.code()}, 메시지 = ${response.message()}")
                    onFailure("요청 실패: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TogetherParentsDetailViewModel", "네트워크 오류 발생: ${e.message}", e)
                onFailure("네트워크 오류 발생: ${e.message}")
            }
        }
    }
}
