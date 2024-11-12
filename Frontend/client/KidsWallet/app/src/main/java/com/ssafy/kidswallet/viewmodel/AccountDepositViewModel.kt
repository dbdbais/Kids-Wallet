package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.AccountDepositModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountDepositViewModel : ViewModel() {

    private val _depositSuccess = MutableStateFlow<Boolean?>(null)
    val depositSuccess: StateFlow<Boolean?> = _depositSuccess

    private val _depositError = MutableStateFlow<String?>(null)
    val depositError: StateFlow<String?> = _depositError

    fun depositFunds(accountId: String, amount: Int) {
        val depositModel = AccountDepositModel(
            accountId = accountId,
            amount = amount
        )
        Log.d("AccountDepositViewModel", "Sending PATCH request: $depositModel") // 요청 전송 로그

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.accountDeposit(depositModel)
                if (response.isSuccessful) {
                    Log.d("AccountDepositViewModel", "Request successful: ${response.body()}") // 성공 로그
                    _depositSuccess.value = true
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AccountDepositViewModel", "Request failed: $errorMessage") // 실패 로그
                    _depositError.value = errorMessage
                }
            } catch (e: Exception) {
                val error = "Network error: ${e.message}"
                Log.e("AccountDepositViewModel", error) // 네트워크 오류 로그
                _depositError.value = error
            }
        }
    }
}
