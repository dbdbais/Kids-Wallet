import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.AccountTransferModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountTransferViewModel : ViewModel() {

    private val _transferSuccess = MutableStateFlow<Boolean?>(null)
    val transferSuccess: StateFlow<Boolean?> = _transferSuccess

    private val _transferError = MutableStateFlow<String?>(null)
    val transferError: StateFlow<String?> = _transferError

    fun transferFunds(fromId: String, toId: String, message: String?, amount: Int) {
        val transferModel = AccountTransferModel(
            fromId = fromId,
            toId = toId,
            message = if (message.isNullOrBlank()) null else message, // 빈 문자열일 경우 null로 설정
            amount = amount
        )
        Log.d("AccountTransferViewModel", "Sending PATCH request: $transferModel") // 요청 전송 로그

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.accountTransfer(transferModel)
                if (response.isSuccessful) {
                    Log.d("AccountTransferViewModel", "Request successful: ${response.body()}") // 성공 로그
                    _transferSuccess.value = true
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("AccountTransferViewModel", "Request failed: $errorMessage") // 실패 로그
                    _transferError.value = errorMessage
                }
            } catch (e: Exception) {
                val error = "Network error: ${e.message}"
                Log.e("AccountTransferViewModel", error) // 네트워크 오류 로그
                _transferError.value = error
            }
        }
    }
}
