package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherDetailModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TogetherDetailViewModel : ViewModel() {
    private val _togetherDetail = MutableStateFlow<TogetherDetailModel?>(null)
    val togetherDetail: StateFlow<TogetherDetailModel?> = _togetherDetail

    fun fetchTogetherDetail(savingContractId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.togetherDetail(savingContractId)
                if (response.isSuccessful) {
                    _togetherDetail.value = response.body()?.data
                    Log.d("TogetherDetailViewModel", "Fetched detail: ${_togetherDetail.value}")
                } else {
                    Log.e("TogetherDetailViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TogetherDetailViewModel", "Network error: ${e.message}")
            }
        }
    }
}
