package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.LoginModel
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _userData = MutableStateFlow<UserDataModel?>(null)
    val userData: StateFlow<UserDataModel?> = _userData

    fun loginUser(userName: String, userPassword: String) {
        val loginModel = LoginModel(userName, userPassword)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.loginUser(loginModel)
                if (response.isSuccessful) {
                    _loginState.value = true

                    val apiResponse = response.body()
                    val userData = apiResponse?.data
                    if (userData != null) {
                        _userData.value = userData
                    }

                    Log.d("LoginSuccess", "Response Body: ${response.body()}")
                    Log.d("FullResponse", "Response: $response")
                    Log.d("UserDataState", "Current user data: ${_userData.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        try {
                            val jsonObject = JSONObject(it)
                            jsonObject.getString("message") // "message" 필드의 값을 추출
                        } catch (e: Exception) {
                            "An error occurred"
                        }
                    } ?: "An error occurred"

                    _errorState.value = "로그인 실패: $errorMessage"
                    _loginState.value = false
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _loginState.value = false
            }
        }
    }
}