package com.ssafy.kidswallet.ui.screens.begging.beggingmoney

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.components.LightBlueButton
import com.ssafy.kidswallet.viewmodel.BeggingAmountViewModel
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BeggingRequestScreen(navController: NavController, name: String?, viewModel: BeggingAmountViewModel = viewModel()) {
    val amount by viewModel.amount.collectAsState()
    Column(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Top(title = "조르기", navController = navController) // BackButton 사용
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Color(0xFFE9F8FE)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name ?: "알 수 없는 사용자",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)

                )
                Text(
                    text = "에게",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Text(
                text = "얼마를 요청할까요?",
                style = FontSizes.h24,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_coin),
                contentDescription = "Coin with Amount",
                modifier = Modifier
                    .width(80.dp) // 가로 크기 조절
                    .height(80.dp) // 세로 크기 조절
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularSlider(amount = amount, onAmountChange = { newAmount -> viewModel.setAmount(newAmount) })
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp, start = 16.dp, end = 16.dp)
        ) {
            BlueButton(
                onClick = { navController.navigate("beggingRequestReason?name=${name}&amount=${amount}") },
                text = "다음",
                modifier = Modifier
                    .width(400.dp) // 원하는 너비 설정
                    .align(Alignment.BottomCenter), // 화면 하단 중앙에 정렬
                height = 50,
                elevation = 4
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CircularSlider(
    amount: Int,
    onAmountChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxAmount: Int = 50000,
    maxInputAmount: Int = 10000000,
    handleRadius: Float = 72f,
    trackThickness: Float = 64f
) {

    var showDialog by remember { mutableStateOf(false) } // 다이얼로그 표시 여부
    var inputAmount by remember { mutableStateOf(TextFieldValue(amount.toString())) }

    val sweepAngle by derivedStateOf { (amount.toFloat() / maxAmount) * 360f } // 슬라이더 각도 계산

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        // Canvas를 사용하여 원형 슬라이더 그리기
        Canvas(
            modifier = Modifier
                .size(250.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        // Canvas 중심을 기준으로 드래그 위치를 가져옴
                        val x = change.position.x - size.width / 2
                        val y = change.position.y - size.height / 2
                        val distanceFromCenter = sqrt(x * x + y * y)
                        val radius = minOf(size.width, size.height) / 2

                        // 터치가 핸들 근처에 있는지 확인 (핸들 위치 기준 반경 설정)
                        val handleRadius = 30.dp.toPx() // 핸들 근처에 있는지 확인할 반경
                        var angle = atan2(y, x) * (180 / PI).toFloat() + 180f - 90f
                        // 0 ~ 360 사이로 보정
                        if (angle < 0) angle += 360
                        angle %= 360

                        if (distanceFromCenter in (radius - handleRadius)..(radius + handleRadius)) {
                            // 슬라이더 값을 1000 단위로 업데이트
                            val newAmount = ((angle / 360f) * maxAmount)
                                .roundToInt()
                                .coerceIn(0, maxAmount)
                            onAmountChange((newAmount / 1000) * 1000 + 1000)
                        }
                    }
                }
        ) {
            val radius = size.minDimension / 2
            val thickness = 20f

            // 배경 원
            drawCircle(
                color = Color(0xFFE0F7FA),
                radius = radius - thickness / 2,
                style = Stroke(width = trackThickness)
            )

            // 진행 원 (현재 값까지 채워진 부분)
            drawArc(
                color = Color(0xFF4FC3F7),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = trackThickness)
            )

            // 핸들 위치 계산
            val angleRad = (sweepAngle - 90f) * (PI / 180f).toFloat()
            val handleX = (radius * cos(angleRad)) + center.x
            val handleY = (radius * sin(angleRad)) + center.y

            // 핸들 그리기
            drawCircle(
                color = Color.White,
                radius = handleRadius, // 핸들 반지름
                center = Offset(handleX, handleY)
            )

            // 파란색 테두리 원 그리기
            drawCircle(
                color = Color(0xFF99DDF8), // 파란색 테두리 색상
                radius = handleRadius, // 핸들 반지름
                center = Offset(handleX, handleY),
                style = Stroke(width = 6f) // 테두리 두께 설정
            )
        }

        // 텍스트 표시 (현재 금액)
        val formattedNumber = NumberUtils.formatNumberWithCommas(amount)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${formattedNumber}원", style = FontSizes.h24, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LightBlueButton(
                onClick = { showDialog = true },
                text = "직접입력",
                height = 40,
                elevation = 0
            )
        }
        // 직접 입력을 위한 다이얼로그
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "금액 입력") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputAmount,
                            onValueChange = {
                                val filteredInput = it.text.filter { char -> char.isDigit() } // 숫자만 허용
                                if (filteredInput.length <= 8) { // 9자리 제한
                                    inputAmount = TextFieldValue(
                                        text = filteredInput,
                                        selection = TextRange(filteredInput.length) // 커서를 항상 텍스트 끝에 위치하도록 설정
                                    )
                                }
                            },
                            label = { Text("금액") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                },
                containerColor = Color.White,
                confirmButton = {
                    BlueButton(
                        onClick = {
                            inputAmount.text.toIntOrNull()?.let {
                                onAmountChange(it.coerceIn(0, maxInputAmount))
                            }
                            showDialog = false
                        },
                        height = 40,
                        modifier = Modifier.width(130.dp), // 원하는 너비 설정
                        elevation = 0,
                        text = "확인"
                    )
                },
                dismissButton = {
                    GrayButton(
                        onClick = { showDialog = false },
                        height = 40,
                        modifier = Modifier.width(130.dp), // 원하는 너비 설정
                        elevation = 0,
                        text = "취소"
                    )
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun BeggingRequestScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    BeggingRequestScreen(navController = navController, name = "테스트 사용자") // 테스트용 이름 전달
}