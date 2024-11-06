import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ssafy.kidswallet.data.model.TextModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BeggingReasonViewModel : ViewModel() {
    private val _textModel = MutableStateFlow(TextModel(text = ""))
    val textModel: StateFlow<TextModel> get() = _textModel

    fun setText(newText: String) {
        _textModel.value = _textModel.value.copy(text = newText)
    }

    fun resetText() {
        _textModel.value = TextModel(text = "")
    }
}