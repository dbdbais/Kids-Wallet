package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.RelationModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RelationViewModel : ViewModel() {

    private val _relationState = MutableStateFlow<Boolean?>(null)
    val relationState: StateFlow<Boolean?> = _relationState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun addRelation(relationModel: RelationModel) {
        viewModelScope.launch {
            try {
                val response: Response<Any> = RetrofitClient.apiService.addRelation(relationModel)
                if (response.isSuccessful) {
                    _relationState.value = true
                    Log.d("RelationViewModel", "Relation added successfully.")
                } else {
                    _relationState.value = false
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to add relation: $errorMessage"
                }
            } catch (e: Exception) {
                _relationState.value = false
                _errorState.value = "Network error: ${e.message}"
            }
        }
    }
}