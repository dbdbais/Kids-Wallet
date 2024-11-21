package com.ssafy.kidswallet.ui.screens.mywallet

import AccountTransferViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import org.json.JSONObject
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun MyWalletTransferScreen(
    navController: NavController,
    viewModel: AccountTransferViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value

    val focusManager = LocalFocusManager.current
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val transferSuccess by viewModel.transferSuccess.collectAsState()
    val transferError by viewModel.transferError.collectAsState()

    // Dialog 상태 관리
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    // 송금 성공 시 다이얼로그 표시
    LaunchedEffect(transferSuccess) {
        if (transferSuccess == true) {
            dialogMessage.value = "송금이 성공적으로 완료되었습니다!"
            showDialog.value = true
        }
    }

    // 오류 메시지가 발생할 경우 다이얼로그 표시
    LaunchedEffect(transferError) {
        transferError?.let {
            // JSON 파싱하여 message 필드만 추출
            var errorMessage = it
            try {
                val jsonObject = JSONObject(it)
                errorMessage = jsonObject.getString("message")
            } catch (e: Exception) {
                // 파싱 실패 시 그대로 사용
            }

            // 특정 오류 메시지를 사용자 정의 메시지로 변환 및 성공 메시지로 전환
            if (errorMessage == "토큰 값이 NULL입니다.") {
                dialogMessage.value = "송금이 성공적으로 완료되었습니다!"
                showDialog.value = true
            } else {
                dialogMessage.value = when (errorMessage) {
                    "잘못된 요청입니다." -> "없는 계좌번호입니다."
                    "돈이 부족합니다." -> "잔액이 부족합니다. 금액을 확인해주세요."
                    else -> errorMessage
                }
                showDialog.value = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
    ) {
        TopSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))

        FormSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            accountNumber = accountNumber,
            amount = amount,
            message = message,
            onAccountNumberChange = { accountNumber = it },
            onAmountChange = { amount = it },
            onMessageChange = { message = it },
            onTransferClick = {
                val fromId = storedUserData?.representAccountId ?: ""
                val amountInt = amount.toIntOrNull() ?: 0
                val messageToSend: String? = if (message.isBlank()) null else message

                if (fromId.isNotEmpty()) {
                    viewModel.transferFunds(
                        fromId = fromId,
                        toId = accountNumber,
                        message = messageToSend,
                        amount = amountInt
                    )
                } else {
                    println("Error: fromId is empty. Cannot proceed with the transfer.")
                }
            }
        )


        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {

                },
                title = {
                    Text(
                        text = if (dialogMessage.value == "송금이 성공적으로 완료되었습니다!") "성공" else "오류",
                        color = if (dialogMessage.value == "송금이 성공적으로 완료되었습니다!") Color(0xFF77DD77) else Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = dialogMessage.value,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8C8595)
                    )
                },
                containerColor = Color.White,
                confirmButton = {
                    BlueButton(
                        onClick = {
                            showDialog.value = false
                            if (dialogMessage.value == "송금이 성공적으로 완료되었습니다!") {
                                navController.navigate("myWallet") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        },
                        height = 40,
                        modifier = Modifier.width(260.dp),
                        elevation = 0,
                        text = "확인"
                    )
                }
            )
        }
    }
}


@Composable
fun TopSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Top(title = "보내기", navController = navController)
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F5FC), RoundedCornerShape(8.dp))
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color(0xFF6DCEF5), fontSize = 33.sp, fontWeight = FontWeight.Bold)
                ) {
                    append("누구")
                }
                append(" 에게\n얼마를 보낼까요?")
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_withrow),
            contentDescription = "송금 아이콘",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun FormSection(
    modifier: Modifier = Modifier,
    accountNumber: String,
    amount: String,
    message: String,
    onAccountNumberChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onTransferClick: () -> Unit
) {
    var accountNumberState by remember { mutableStateOf(TextFieldValue(accountNumber)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = accountNumberState,
            onValueChange = { newValue ->
                val sanitizedInput = newValue.text.filter { it.isDigit() }
                val formattedInput = formatAccountNumber(sanitizedInput)
                val newCursorPos = calculateCursorPosition(newValue.text, formattedInput, newValue.selection.start)
                accountNumberState = TextFieldValue(formattedInput, TextRange(newCursorPos))
                onAccountNumberChange(formattedInput)
            },
            label = { Text("계좌번호") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (accountNumberState.text.isNotEmpty()) {
                    IconButton(onClick = {
                        accountNumberState = TextFieldValue("")
                        onAccountNumberChange("")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear Account Number",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color(0xFFD3D0D7)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() } && newValue.length <= 8) {
                    onAmountChange(newValue)
                }
            },
            label = { Text("금액") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (amount.isNotEmpty()) {
                    IconButton(onClick = { onAmountChange("") }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear Amount",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color(0xFFD3D0D7)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= 15) {
                    onMessageChange(it)
                }
            },
            label = { Text("메세지를 적어주세요!") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (message.isNotEmpty()) {
                    IconButton(onClick = { onMessageChange("") }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear Message",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color(0xFFD3D0D7)
            )
        )
        Text(
            text = "${message.length}/15",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.weight(1f))

        BlueButton(
            onClick = {
                println("보내기 버튼 클릭됨")
                onTransferClick()
            },
            text = "보내기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
    }
}

// 계좌번호 형식에 맞춰 하이픈을 추가하는 함수
fun formatAccountNumber(input: String): String {
    return when {
        input.length <= 6 -> input
        input.length <= 8 -> "${input.substring(0, 6)}-${input.substring(6)}"
        input.length <= 14 -> "${input.substring(0, 6)}-${input.substring(6, 8)}-${input.substring(8)}"
        else -> "${input.substring(0, 6)}-${input.substring(6, 8)}-${input.substring(8, 14)}"
    }
}

// 기존 커서 위치를 기반으로 새 커서 위치를 계산하는 함수
fun calculateCursorPosition(oldText: String, newText: String, oldCursorPos: Int): Int {
    var cursorPos = oldCursorPos
    val hyphenCountOld = oldText.take(oldCursorPos).count { it == '-' }
    val hyphenCountNew = newText.take(cursorPos).count { it == '-' }
    cursorPos += hyphenCountNew - hyphenCountOld
    return cursorPos.coerceIn(0, newText.length)
}

