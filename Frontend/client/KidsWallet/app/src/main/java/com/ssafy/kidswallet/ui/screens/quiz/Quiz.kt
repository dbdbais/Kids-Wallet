import android.widget.Toast
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun QuizScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(1500) // 로딩 시간 조절
        isLoading = false
    }
    val quiz = listOf(
        mapOf(
            "title" to "은행에 돈을 저축하면 받을 수 있는 것은 무엇일까요?",
            "options" to listOf("1. 이자", "2. 세금", "3. 수수료", "4. 벌금"),
            "comment" to "은행에 돈을 저축하면 일정 기간 돈을 맡긴 대가로 '이자'라는 추가 금액을 받을 수 있습니다",
            "answer" to "1. 이자"
        ),
        mapOf(
            "title" to "신용카드를 사용했을 때 나중에 지불해야 하는 금액은 무엇인가요?",
            "options" to listOf("1. 원금", "2. 연체금", "3. 이자", "4. 청구서"),
            "comment" to "신용카드를 사용하면 사용 내역이 모여 '청구서'로 발송되며, 정해진 기한 내에 지불해야 합니다.",
            "answer" to "4. 청구서"
        )
    )
    val randomQuiz = quiz[Random.nextInt(quiz.size)]

    val options = randomQuiz["options"] as? List<*>

    val selectedOptionIndex = remember { mutableIntStateOf(-1) }

    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(32.dp),
                        )
                        .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(32.dp))
                        .padding(32.dp)
                        .size(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomLoadingIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(32.dp),
                        )
                        .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(32.dp))
                        .padding(32.dp)
                ) {
                    Text(
                        text = "키즈 쑥쑥 퀴즈",
                        color = Color(0xFF6DCEF5),
                        fontWeight = FontWeight.Bold,
                        style = FontSizes.h16
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = randomQuiz["title"] as String,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = FontSizes.h24
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4개의 보기 그리드
                    Column {
                        options?.chunked(2)?.forEachIndexed { rowIndex, rowOptions ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowOptions.forEachIndexed { index, option ->
                                    val optionText = option as? String ?: ""
                                    val globalIndex = rowIndex * 2 + index // 전체 인덱스를 추적

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                            .padding(8.dp)
                                            .clickable(
                                                onClick = {
                                                    selectedOptionIndex.value = globalIndex
                                                },
                                                indication = null, // Removes the click effect
                                                interactionSource = remember { MutableInteractionSource() } // Prevents default interaction behavior
                                            ),
                                        contentAlignment = if (index == 0) Alignment.CenterStart else Alignment.CenterEnd
                                    ) {
                                        Text(
                                            text = optionText,
                                            color = if (selectedOptionIndex.value == globalIndex) Color(0xFF0000FF) else Color.Black, // 선택된 경우 파란색으로 변경
                                            style = FontSizes.h20,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }
            if (selectedOptionIndex.value == -1) {
                // LightGrayButton을 표시하는 경우
                LightGrayButton(
                    onClick = {

                    },
                    modifier = Modifier.width(380.dp),
                    height = 40,
                    text = "제출하기"
                )
            } else {
                // BlueButton을 표시하는 경우
                BlueButton(
                    onClick = {
                        val selectedOption = options?.getOrNull(selectedOptionIndex.value)
                        if (selectedOption == randomQuiz["answer"]) {
                            dialogMessage.value = "정답입니다!"
                        } else {
                            dialogMessage.value = "오답입니다!"
                        }
                        showDialog.value = true
                    },
                    modifier = Modifier.width(380.dp),
                    height = 40,
                    text = "제출하기"
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logo_full), // 이미지 리소스를 불러옵니다.
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // 원하는 위치에 배치할 수 있도록 설정
                    .size(258.dp), // 원하는 크기 설정
            )

        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                if (dialogMessage.value == "정답입니다!") {
                    showDialog.value = true
                }},
            title = {
                Text(
                    text = dialogMessage.value,
                    color = if (dialogMessage.value == "정답입니다!") Color(0xFF77DD77) else Color(0xFFFFA1B0),
                    fontWeight = FontWeight.Bold // Makes the text bold
                )
            },
            text = {
                Text(
                    text = if (dialogMessage.value == "정답입니다!") {
                        randomQuiz["comment"] as String
                    } else {
                        "거의 다 왔어요! 다시 생각해보아요!"
                    },
                    fontWeight = FontWeight.Bold
                )
            },
            containerColor = Color.White,
            confirmButton = {
                BlueButton(
                    onClick = {
                        showDialog.value = false
                        if (dialogMessage.value == "정답입니다!") {
                            navController.navigate("mainPage") // Navigate to the main page
                        }
                    },
                    height = 40,
                    modifier = Modifier.width(260.dp), // 원하는 너비 설정
                    elevation = 0,
                    text = "확인"
                )
            }
        )
    }
}

@Composable
fun CustomLoadingIndicator() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .size(150.dp)
            .graphicsLayer {
                rotationZ = rotation
            }
            .background(
                brush = Brush.verticalGradient( // 수직 방향으로 그라데이션 적용
                    colors = listOf(
                        Color(0xFF99DDF8), // 시작 색상
                        Color(0xFF6DCEF5)  // 끝 색상
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "로딩", color = Color.White, fontWeight = FontWeight.Bold)
    }
}
