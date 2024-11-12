package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.BeggingRequestModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.launch
import retrofit2.Response

class BeggingRequestViewModel : ViewModel() {

    fun sendBeggingRequest(
        userId: String,
        toUserId: String,
        message: String,
        amount: Int,
        onSuccess: (Response<Any>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val request = BeggingRequestModel(
            userId = userId,
            toUserId = toUserId,
            beggingMessage = message,
            beggingMoney = amount
        )

        Log.d("BeggingRequestViewModel", "Sending request: $request")

        viewModelScope.launch {
            try {
                val response = apiService.beggingRequest(request)
                if (response.isSuccessful) {
                    Log.d("BeggingRequestViewModel", "Request successful: ${response.body()}")
                    onSuccess(response)
                } else {
                    val errorMessage = "API Error: ${response.code()} - ${response.message()}"
                    Log.e("BeggingRequestViewModel", errorMessage)
                    onError(Exception("API Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}