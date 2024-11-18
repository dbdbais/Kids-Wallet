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
    // 필요하지 않은 경우 상태 관리 제거
    private val _togetherDetailResponse = MutableStateFlow<TogetherDetailResponse?>(null)

    // acceptTogetherRun 함수는 그대로 두되, 필요 시 추가적인 상태 관리 로직을 추가
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
                    _togetherDetailResponse.value = response.body() // 필요 시 UI에서 사용
                    onSuccess()
                } else {
                    onFailure("요청 실패: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                onFailure("네트워크 오류 발생: ${e.message}")
            }
        }
    }
}

