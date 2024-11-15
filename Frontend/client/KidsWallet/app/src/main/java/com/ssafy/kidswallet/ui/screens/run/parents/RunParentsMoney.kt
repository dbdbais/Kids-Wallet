package com.ssafy.kidswallet.ui.screens.run.parents

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightBlueButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.CircularSlider
import com.ssafy.kidswallet.viewmodel.RunParentsAmountViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.screens.run.viewmodel.state.StateRunViewModel
import com.ssafy.kidswallet.viewmodel.state.StateRunMoneyViewModel
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun RunParentsMoneyScreen(
    navController: NavController,
    amountViewModel: RunParentsAmountViewModel = viewModel(),
    stateRunMoneyViewModel: StateRunMoneyViewModel = viewModel()
) {
    val childGoalMoney = stateRunMoneyViewModel.childGoalMoney
    val parentGoalMoney = stateRunMoneyViewModel.parentGoalMoney
    val togetherGoalMoney = stateRunMoneyViewModel.togetherGoalMoney

    // 각각의 금액 행에 대한 편집 상태 관리
    var isEditingChildAmount by remember { mutableStateOf(false) }
    var isEditingParentAmount by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // 외부 클릭 시 모든 편집 종료
                    isEditingChildAmount = false
                    isEditingParentAmount = false
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "목표 금액 설정",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        RCircularSlider(
            amount = togetherGoalMoney,
            onAmountChange = { newAmount ->
                stateRunMoneyViewModel.setGoalAndDate(
                    togetherGoal = newAmount,
                    childGoal = childGoalMoney,
                    parentGoal = parentGoalMoney
                )
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 자녀 목표 금액
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character_me),
                    contentDescription = "나",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "나",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }

            EditableAmountRow(
                initialAmount = childGoalMoney,
                onAmountChange = { newAmount ->
                    stateRunMoneyViewModel.setGoalAndDate(
                        togetherGoal = togetherGoalMoney,
                        childGoal = newAmount,
                        parentGoal = parentGoalMoney
                    )
                },
                isEditing = isEditingChildAmount,
                onEditingChange = { isEditingChildAmount = it }
            )
        }

        // 부모 목표 금액
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character_run_member),
                    contentDescription = "응애재훈",
                    modifier = Modifier.size(55.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "응애재훈",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }

            EditableAmountRow(
                initialAmount = parentGoalMoney,
                onAmountChange = { newAmount ->
                    stateRunMoneyViewModel.setGoalAndDate(
                        togetherGoal = togetherGoalMoney,
                        childGoal = childGoalMoney,
                        parentGoal = newAmount
                    )
                },
                isEditing = isEditingParentAmount,
                onEditingChange = { isEditingParentAmount = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        LightBlueButton(
            onClick = { navController.navigate("runParentsMemberList") },
            text = "멤버 선택",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp),
            height = 40,
            elevation = 0
        )

        BlueButton(
            onClick = { navController.navigate("runParentsRegister") },
            text = "다음",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun EditableAmountRow(
    initialAmount: Int,
    onAmountChange: (Int) -> Unit,
    isEditing: Boolean,
    onEditingChange: (Boolean) -> Unit
) {
    var textState by remember { mutableStateOf(initialAmount.toString()) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = textState,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() }) {
                        textState = newText
                        onAmountChange(newText.toIntOrNull() ?: 0)
                    }
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 4.dp)
            )
        } else {
            Text(
                text = "${textState}원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_edit),
            contentDescription = "수정",
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    onEditingChange(true)
                }
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun RCircularSlider(
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${amount}원", style = FontSizes.h24, fontWeight = FontWeight.Bold)
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
                            onValueChange = { inputAmount = it },
                            label = { Text("금액") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsMoneyScreenPreview() {
    RunParentsMoneyScreen(navController = rememberNavController())
}