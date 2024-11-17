package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.RegularListModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegularListViewModel : ViewModel() {

    private val _regularList = MutableStateFlow<List<RegularListModel>>(emptyList())
    val regularList: StateFlow<List<RegularListModel>> = _regularList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchRegularList(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getRegularDepositList(userId)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _regularList.value = it
                    }
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}