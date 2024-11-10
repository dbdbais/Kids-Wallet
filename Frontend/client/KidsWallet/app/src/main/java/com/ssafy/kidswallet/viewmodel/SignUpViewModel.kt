package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ssafy.kidswallet.data.model.SignUpModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpModel?>(null)
    val signUpState: StateFlow<SignUpModel?> = _signUpState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    // 사용자 데이터 입력 처리
    fun registerUser(
        userName: String,
        userPassword: String,
        userBirth: String,
        userGender: String,
        userRealName: String,
        userRole: String
    ) {

        viewModelScope.launch {
            // 데이터 검증 로직 (예시)
            val formattedBirthDate = try {
                val inputFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date: Date = inputFormat.parse(userBirth)!!
                outputFormat.format(date) // 변환된 문자열
            } catch (e: Exception) {
                _errorState.value = "Invalid date format: ${e.message}"
                Log.e("SignUpViewModel", "Invalid date format", e)
                return@launch
            }

            // 사용자 모델 생성
            val signUpModel = SignUpModel(
                userName = userName,
                userPassword = userPassword,
                userBirth = formattedBirthDate,
                userGender = userGender,
                userRealName = userRealName,
                userRole = userRole
            )

            try {
                val response = RetrofitClient.apiService.registerUser(signUpModel)
                if (response.isSuccessful) {
                    _signUpState.value = signUpModel
                    Log.d("SignUpViewModel", "User registered successfully")
                } else {
                    _errorState.value = "Registration failed: ${response.errorBody()?.string()}"
                    Log.e("SignUpViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception){
                _errorState.value = "Network error: ${e.message}"
                Log.e("SignUpViewModel", "Exception: ${e.message}", e)
            }

        }
    }
}
