package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherCompleteListModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TogetherCompleteListViewModel : ViewModel() {
    private val _togetherCompleteList = MutableStateFlow<List<TogetherCompleteListModel>>(emptyList())
    val togetherCompleteList: StateFlow<List<TogetherCompleteListModel>> = _togetherCompleteList

    fun fetchTogetherCompleteList(userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.togetherCompleteList(userId)
                if (response.isSuccessful) {
                    _togetherCompleteList.value = response.body()?.data ?: emptyList()
                    Log.d("TogetherCompleteListViewModel", "Fetched complete together list: ${_togetherCompleteList.value}")
                } else {
                    Log.e("TogetherCompleteListViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TogetherCompleteListViewModel", "Network error: ${e.message}")
            }
        }
    }
}
