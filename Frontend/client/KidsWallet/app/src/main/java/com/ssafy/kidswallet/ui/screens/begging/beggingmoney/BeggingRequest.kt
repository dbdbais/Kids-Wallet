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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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
            Top(title = "조르기", navController = navController) 
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
                    .width(80.dp) 
                    .height(80.dp) 
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
            LineSlider(amount = amount, onAmountChange = { newAmount -> viewModel.setAmount(newAmount) })
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
                    .width(400.dp) 
                    .align(Alignment.BottomCenter), 
                height = 50,
                elevation = 4
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun LineSlider(
    amount: Int,
    onAmountChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxAmount: Int = 50000,
    maxInputAmount: Int = 10000000,
    trackLength: Dp = 300.dp, 
    handleRadius: Dp = 15.dp, 
    trackThickness: Dp = 4.dp 
) {
    val progress by derivedStateOf { (amount.toFloat() / maxAmount) } 
    val formattedNumber = NumberUtils.formatNumberWithCommas(amount)
    var showDialog by remember { mutableStateOf(false) } 
    var inputAmount by remember { mutableStateOf(TextFieldValue(amount.toString())) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .width(trackLength),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${formattedNumber}원",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .width(trackLength)
                .height(handleRadius * 2)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        
                        val offsetX = change.position.x.coerceIn(0f, trackLength.toPx())
                        val newProgress = offsetX / trackLength.toPx()
                        val newAmount = ((newProgress * maxAmount) / 1000).roundToInt() * 1000
                        onAmountChange(newAmount)
                    }
                }
        ) {
            val density = LocalDensity.current
            val handlePositionX = (with(density) { progress * trackLength.toPx() }).coerceIn(
                0f,
                with(density) { trackLength.toPx() }
            )

            
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gradient = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFCCE6FF), Color(0xFF99DDF8), Color(0xFFCCE6FF)),
                    startX = 0f,
                    endX = size.width
                )
                drawLine(
                    brush = gradient,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = trackThickness.toPx(),
                    cap = StrokeCap.Round 
                )

                
                val tickSpacing = size.width / 20
                for (i in 0..20) { 
                    val x = i * tickSpacing
                    val isMultipleOfFour = i % 4 == 0 
                    drawLine(
                        color = Color(0xFFCCE6FF),
                        start = Offset(x, size.height / 2 - if (isMultipleOfFour) 20.dp.toPx() else 12.dp.toPx()), 
                        end = Offset(x, size.height / 2 + if (isMultipleOfFour) 20.dp.toPx() else 12.dp.toPx()), 
                        strokeWidth = if (isMultipleOfFour) 6f else 3f 
                    )
                }
            }

            Canvas(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            (handlePositionX - handleRadius.toPx()).roundToInt(), 
                            0
                        )
                    }
                    .size(handleRadius * 2)
            ) {
                
                drawCircle(
                    color = Color(0xFF99DDF8),
                    radius = handleRadius.toPx() * 1.7f,
                    style = Stroke(width = 6f)
                )

                
                drawCircle(
                    color = Color.White,
                    radius = handleRadius.toPx() * 1.55f
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

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
                        onValueChange = {
                            val filteredInput = it.text.filter { char -> char.isDigit() } 
                            if (filteredInput.length <= 8) { 
                                inputAmount = TextFieldValue(
                                    text = filteredInput,
                                    selection = TextRange(filteredInput.length) 
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

                        
                        if (angle > 360f) {
                            angle = 360f
                        }

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
        
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "금액 입력") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputAmount,
                            onValueChange = {
                                val filteredInput = it.text.filter { char -> char.isDigit() } 
                                if (filteredInput.length <= 8) { 
                                    inputAmount = TextFieldValue(
                                        text = filteredInput,
                                        selection = TextRange(filteredInput.length) 
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

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", 
    showSystemUi = true
)
@Composable
fun BeggingRequestScreenPreview() {
    
    val navController = rememberNavController()
    BeggingRequestScreen(navController = navController, name = "테스트 사용자") 
}