package com.ssafy.kidswallet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherrunReisterModel
import com.ssafy.kidswallet.data.network.ApiService
import kotlinx.coroutines.launch

class TogetherrunReisterViewModel(
    private val apiService: ApiService // ApiService를 생성자로 받음
) : ViewModel() {

    fun registerTogetherrun(
        requestModel: TogetherrunReisterModel,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                println("Sending Request: $requestModel") // 요청 내용 디버깅 출력
                val response = apiService.registerTogetherrun(requestModel)
                println("Response Code: ${response.code()}, Message: ${response.message()}") // 응답 상태 디버깅
                println("Response Body: ${response.body()}") // 응답 본문 디버깅

                onResult(response.isSuccessful)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error occurred: ${e.message}") // 예외 디버깅
                onResult(false)
            }
        }
    }
}
