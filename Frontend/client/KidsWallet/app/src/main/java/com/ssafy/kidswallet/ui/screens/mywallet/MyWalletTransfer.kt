package com.ssafy.kidswallet.ui.screens.mywallet

import AccountTransferViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.LoginViewModel

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
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
                val messageToSend: String? = if (message.isBlank()) null else message // 빈 문자열이면 null로 설정

                if (fromId.isNotEmpty()) {
                    // fromId가 유효할 때만 요청을 보냅니다.
                    viewModel.transferFunds(fromId = fromId, toId = accountNumber, message = messageToSend, amount = amountInt)
                } else {
                    println("Error: fromId is empty. Cannot proceed with the transfer.")
                }
            }
        )

        // 성공 또는 오류 메시지 표시
        when {
            transferSuccess == true -> {
                Text(
                    text = "송금이 성공적으로 완료되었습니다!",
                    color = Color.Green,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                LaunchedEffect(Unit) {
                    navController.navigate("mainPage") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            transferError != null -> {
                Text(
                    text = transferError ?: "송금 오류가 발생했습니다.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
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
            painter = painterResource(id = R.drawable.logo_coin),
            contentDescription = "송금 아이콘",
            modifier = Modifier.size(48.dp)
        )
    }
}

// 계좌번호 형식에 맞춰 자동으로 하이픈을 추가하는 함수
fun formatAccountNumber(input: String): String {
    return when {
        input.length <= 6 -> input
        input.length <= 8 -> "${input.substring(0, 6)}-${input.substring(6)}"
        input.length <= 14 -> "${input.substring(0, 6)}-${input.substring(6, 8)}-${input.substring(8)}"
        else -> "${input.substring(0, 6)}-${input.substring(6, 8)}-${input.substring(8, 14)}"
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = accountNumber,
            onValueChange = {
                val sanitizedInput = it.filter { char -> char.isDigit() } // 숫자만 남기기
                val formattedInput = formatAccountNumber(sanitizedInput)
                onAccountNumberChange(formattedInput)
            },
            label = { Text("계좌번호") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (accountNumber.isNotEmpty()) {
                    IconButton(onClick = { onAccountNumberChange("") }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear Account Number",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color(0xFFD3D0D7)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color(0xFFD3D0D7)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= 15) { // 15자 이하로 제한
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
            maxLines = 1,
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
            onClick = onTransferClick,
            text = "보내기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MyWalletTransferScreenPreview() {
    MyWalletTransferScreen(navController = rememberNavController())
}
