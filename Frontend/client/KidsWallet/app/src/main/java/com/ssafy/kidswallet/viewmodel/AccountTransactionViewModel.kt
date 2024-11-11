package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.AccountModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountTransactionViewModel : ViewModel() {

    private val _accountState = MutableStateFlow<AccountModel?>(null)
    val accountState: StateFlow<AccountModel?> = _accountState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getTransactionData(accountId: String) {
        viewModelScope.launch {
            try {
                val response: Response<AccountModel> = RetrofitClient.apiService.viewTransaction(accountId)
                if (response.isSuccessful) {
                    _accountState.value = response.body()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to fetch transactions: $errorMessage"
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
            }
        }
    }
}


