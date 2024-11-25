package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.AccountWeeklyModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountWeeklyViewModel : ViewModel() {
    private val _accountWeeklyData = MutableStateFlow<AccountWeeklyModel?>(null)
    val accountWeeklyData: StateFlow<AccountWeeklyModel?> = _accountWeeklyData

    fun fetchAccountWeekly(accountId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAccountWeekly(accountId)
                if (response.isSuccessful) {
                    _accountWeeklyData.value = response.body()?.data
                } else {
                    Log.e("AccountWeeklyViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("AccountWeeklyViewModel", "Network error: ${e.message}")
            }
        }
    }
}
