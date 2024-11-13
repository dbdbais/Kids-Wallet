package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherListModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TogetherListViewModel : ViewModel() {
    private val _togetherList = MutableStateFlow<List<TogetherListModel>>(emptyList())
    val togetherList: StateFlow<List<TogetherListModel>> = _togetherList

    fun fetchTogetherList(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.togetherList(userId)
                if (response.isSuccessful) {
                    _togetherList.value = response.body()?.data ?: emptyList()
                    Log.d("TogetherListViewModel", "Fetched together list: ${_togetherList.value}")
                } else {
                    Log.e("TogetherListViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TogetherListViewModel", "Network error: ${e.message}")
            }
        }
    }
}
