package com.ssafy.kidswallet.ui.screens.run.parents

import android.annotation.SuppressLint
import android.util.Log
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
import com.ssafy.kidswallet.viewmodel.RunMemberViewModel
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
    stateRunMoneyViewModel: StateRunMoneyViewModel = viewModel(),
    runMemberViewModel: RunMemberViewModel = viewModel()
) {
    val childGoalMoney = stateRunMoneyViewModel.childGoalMoney
    val parentGoalMoney = stateRunMoneyViewModel.parentGoalMoney
    val togetherGoalMoney = stateRunMoneyViewModel.togetherGoalMoney

    var showAlertDialog by remember { mutableStateOf(false) }
    val selectedUserRealName = runMemberViewModel.selectedUserRealName
    Log.d("RunParentsMoneyScreen", "Displayed RealName: $selectedUserRealName")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
                    val remainingAmount = (togetherGoalMoney - newAmount).coerceAtLeast(0)
                    stateRunMoneyViewModel.setGoalAndDate(
                        togetherGoal = togetherGoalMoney,
                        childGoal = newAmount,
                        parentGoal = remainingAmount
                    )
                }
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
                    contentDescription = "부모",
                    modifier = Modifier.size(55.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = selectedUserRealName ?: "N/A",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }

            EditableAmountRow(
                initialAmount = parentGoalMoney,
                onAmountChange = { newAmount ->
                    val remainingAmount = (togetherGoalMoney - newAmount).coerceAtLeast(0)
                    stateRunMoneyViewModel.setGoalAndDate(
                        togetherGoal = togetherGoalMoney,
                        childGoal = remainingAmount,
                        parentGoal = newAmount
                    )
                }
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
            onClick = {
                if (togetherGoalMoney == childGoalMoney + parentGoalMoney) {
                    navController.navigate("runParentsRegister")
                } else {
                    showAlertDialog = true
                }
            },
            text = "다음",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = {
                    Text(
                        text = "알림",
                        color = Color(0xFFFBC02D),
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "자녀와 부모의 목표금액을 확인해 주세요.",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8C8595)
                    )
                },
                confirmButton = {
                    BlueButton(
                        onClick = { showAlertDialog = false },
                        text = "확인",
                        modifier = Modifier.width(260.dp),
                        height = 40,
                        elevation = 0
                    )
                }
            )
        }
    }
}


@Composable
fun EditableAmountRow(
    initialAmount: Int,
    onAmountChange: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            showDialog = true
        }
    ) {
        Text(
            text = "${NumberUtils.formatNumberWithCommas(initialAmount)}원",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_edit),
            contentDescription = "수정",
            modifier = Modifier
                .size(25.dp)
        )
    }

    if (showDialog) {
        AmountInputDialog(
            initialAmount = initialAmount,
            onConfirm = { newAmount ->
                onAmountChange(newAmount)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}


@Composable
fun AmountInputDialog(
    initialAmount: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var inputAmount by remember { mutableStateOf(TextFieldValue(initialAmount.toString())) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "금액 입력") },
        text = {
            Column {
                OutlinedTextField(
                    value = inputAmount,
                    onValueChange = { newValue ->
                        if (newValue.text.all { it.isDigit() } && newValue.text.length <= 8) {
                            inputAmount = newValue
                        }
                    },
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
                        onConfirm(it)
                    }
                    onDismiss()
                },
                height = 40,
                modifier = Modifier.width(130.dp),
                elevation = 0,
                text = "확인"
            )
        },
        dismissButton = {
            GrayButton(
                onClick = { onDismiss() },
                height = 40,
                modifier = Modifier.width(130.dp),
                elevation = 0,
                text = "취소"
            )
        }
    )
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

    var showDialog by remember { mutableStateOf(false) }
    var inputAmount by remember { mutableStateOf(TextFieldValue(amount.toString())) }

    val sweepAngle by derivedStateOf { (amount.toFloat() / maxAmount) * 360f }

    Box(contentAlignment = Alignment.Center, modifier = modifier) {

        Canvas(
            modifier = Modifier
                .size(250.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->

                        val x = change.position.x - size.width / 2
                        val y = change.position.y - size.height / 2
                        val distanceFromCenter = sqrt(x * x + y * y)
                        val radius = minOf(size.width, size.height) / 2


                        val handleRadius = 30.dp.toPx()
                        var angle = atan2(y, x) * (180 / PI).toFloat() + 180f - 90f

                        if (angle < 0) angle += 360
                        angle %= 360

                        if (distanceFromCenter in (radius - handleRadius)..(radius + handleRadius)) {

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


            drawCircle(
                color = Color(0xFFE0F7FA),
                radius = radius - thickness / 2,
                style = Stroke(width = trackThickness)
            )


            drawArc(
                color = Color(0xFF4FC3F7),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = trackThickness)
            )


            val angleRad = (sweepAngle - 90f) * (PI / 180f).toFloat()
            val handleX = (radius * cos(angleRad)) + center.x
            val handleY = (radius * sin(angleRad)) + center.y


            drawCircle(
                color = Color.White,
                radius = handleRadius,
                center = Offset(handleX, handleY)
            )


            drawCircle(
                color = Color(0xFF99DDF8),
                radius = handleRadius,
                center = Offset(handleX, handleY),
                style = Stroke(width = 6f)
            )
        }


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${NumberUtils.formatNumberWithCommas(amount)}원", style = FontSizes.h24, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LightBlueButton(
                onClick = { showDialog = true },
                text = "직접입력",
                height = 40,
                elevation = 0
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "금액 입력") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputAmount,
                            onValueChange = { newValue ->

                                if (newValue.text.all { it.isDigit() } && newValue.text.length <= 8) {
                                    inputAmount = newValue
                                }
                            },
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
                        modifier = Modifier.width(130.dp),
                        elevation = 0,
                        text = "확인"
                    )
                },
                dismissButton = {
                    GrayButton(
                        onClick = { showDialog = false },
                        height = 40,
                        modifier = Modifier.width(130.dp),
                        elevation = 0,
                        text = "취소"
                    )
                }
            )
        }
    }
}