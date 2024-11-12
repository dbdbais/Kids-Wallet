package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.AccountWithdrawModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountWithdrawViewModel : ViewModel() {

    private val _withdrawSuccess = MutableStateFlow<Boolean?>(null)
    val withdrawSuccess: StateFlow<Boolean?> = _withdrawSuccess

    private val _withdrawError = MutableStateFlow<String?>(null)
    val withdrawError: StateFlow<String?> = _withdrawError

    fun withdrawFunds(accountId: String, amount: Int) {
        val withdrawModel = AccountWithdrawModel(
            accountId = accountId,
            amount = amount
        )
        Log.d("AccountWithdrawViewModel", "Sending PATCH request: $withdrawModel") // 요청 전송 로그

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.accountWithdraw(withdrawModel)
                if (response.isSuccessful) {
                    Log.d("AccountWithdrawViewModel", "Request successful: ${response.body()}") // 성공 로그
                    _withdrawSuccess.value = true
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AccountWithdrawViewModel", "Request failed: $errorMessage") // 실패 로그
                    _withdrawError.value = errorMessage
                }
            } catch (e: Exception) {
                val error = "Network error: ${e.message}"
                Log.e("AccountWithdrawViewModel", error) // 네트워크 오류 로그
                _withdrawError.value = error
            }
        }
    }
}
