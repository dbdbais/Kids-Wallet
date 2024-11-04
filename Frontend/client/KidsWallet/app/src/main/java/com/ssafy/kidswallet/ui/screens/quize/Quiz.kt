import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.ui.components.BackButton
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun QuizScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Top(title = "퀴즈", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "퀴즈 화면", style = MaterialTheme.typography.headlineMedium)
    }
}
