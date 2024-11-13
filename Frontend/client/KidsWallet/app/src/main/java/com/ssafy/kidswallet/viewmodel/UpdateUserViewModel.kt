package com.ssafy.kidswallet.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.kidswallet.data.model.ApiResponse
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.data.model.Relation
import com.ssafy.kidswallet.data.network.RetrofitClient
import com.ssafy.kidswallet.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateUserViewModel(application: Application) : AndroidViewModel(application) {

    private val _updateUserState = MutableStateFlow<Boolean?>(null)
    val updateUserState: StateFlow<Boolean?> = _updateUserState

    private val _updatedUserData = MutableStateFlow<UserDataModel?>(null)
    val updatedUserData: StateFlow<UserDataModel?> = _updatedUserData

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val USER_ID_KEY = intPreferencesKey("user_id")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_GENDER_KEY = stringPreferencesKey("user_gender")
    private val USER_REAL_NAME_KEY = stringPreferencesKey("user_real_name")
    private val USER_MONEY_KEY = intPreferencesKey("user_money")
    private val USER_ROLE_KEY = stringPreferencesKey("user_role")
    private val USER_BIRTH_KEY = stringPreferencesKey("user_birth")
    private val HAS_CARD_KEY = booleanPreferencesKey("has_card")
    private val REPRESENT_ACCOUNT_ID_KEY = stringPreferencesKey("represent_account_id")
    private val RELATIONS_KEY = stringPreferencesKey("relations")

    private val gson = Gson()

    fun getStoredUserData(): StateFlow<UserDataModel?> {
        val dataFlow = getApplication<Application>().applicationContext.dataStore.data
            .map { preferences ->
                UserDataModel(
                    userId = preferences[USER_ID_KEY] ?: 0,
                    userName = preferences[USER_NAME_KEY] ?: "",
                    userGender = preferences[USER_GENDER_KEY] ?: "",
                    userRealName = preferences[USER_REAL_NAME_KEY] ?: "",
                    userMoney = preferences[USER_MONEY_KEY] ?: 0,
                    userRole = preferences[USER_ROLE_KEY] ?: "",
                    userBirth = preferences[USER_BIRTH_KEY]?.split(",")?.map { it.toInt() },
                    hasCard = preferences[HAS_CARD_KEY] ?: false,
                    representAccountId = preferences[REPRESENT_ACCOUNT_ID_KEY],
                    relations = preferences[RELATIONS_KEY]?.let {
                        gson.fromJson(it, object : TypeToken<List<Relation>>() {}.type)
                    }
                )
            }
        val userDataStateFlow = MutableStateFlow<UserDataModel?>(null)
        viewModelScope.launch {
            val userData = dataFlow.first() // Flow에서 첫 값을 읽어옴
            userDataStateFlow.value = userData
        }
        return userDataStateFlow
    }

    fun updateUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<ApiResponse> = RetrofitClient.apiService.updateUser(userId)
                Log.d("Response", "Response Body: ${response.body()}")
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val updatedUserData = apiResponse?.data
                    if (updatedUserData != null) {
                        // Response 처리만 수행
                        _updatedUserData.value = updatedUserData
                        Log.d("UpdateUserSuccess", "User data retrieved successfully: $updatedUserData")
                        _updateUserState.value = true
                    } else {
                        _errorState.value = "Failed to retrieve updated user data."
                        _updateUserState.value = false
                        Log.d("UpdateUserError", "Empty response for user data.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _errorState.value = "Failed to update user data: $errorBody"
                    _updateUserState.value = false
                    Log.d("UpdateUserError", "Failed to update user data: $errorBody")
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _updateUserState.value = false
                Log.d("UpdateUserError", "Network error: ${e.message}")
            }
        }
    }
}
