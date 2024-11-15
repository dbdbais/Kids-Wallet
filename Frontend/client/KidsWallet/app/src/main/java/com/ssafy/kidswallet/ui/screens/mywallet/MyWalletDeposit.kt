package com.ssafy.kidswallet.ui.screens.mywallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.AccountDepositViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel

@Composable
fun MyWalletDepositScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val depositViewModel: AccountDepositViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val depositSuccess by depositViewModel.depositSuccess.collectAsState()
    val depositError by depositViewModel.depositError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, // 터치 피드백을 제거
                interactionSource = remember { MutableInteractionSource() } // 터치 상호작용 상태 관리
            ) {
                // 화면 외부를 터치할 때 포커스를 해제하여 키보드를 닫음
                focusManager.clearFocus()
            },
    ) {
        DTopSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
        DHeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        DFormSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            navController = navController,
            depositViewModel = depositViewModel,
            loginViewModel = loginViewModel
        )

        // 성공 또는 오류 메시지 표시
        when {
            depositSuccess == true -> {
                Text(
                    text = "충전이 성공적으로 완료되었습니다!",
                    color = Color.Green,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                LaunchedEffect(Unit) {
                    navController.navigate("myWallet") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            depositError != null -> {
                Text(
                    text = depositError ?: "충전 오류가 발생했습니다.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun DTopSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Top(title = "채우기", navController = navController)
    }
}

@Composable
fun DHeaderSection() {
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
                    append("Kid’s Wallet")
                }
                append(" 에\n얼마를 채울까요?")
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_deposit),
            contentDescription = "송금 아이콘",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun DFormSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    depositViewModel: AccountDepositViewModel,
    loginViewModel: LoginViewModel
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "내 Kid's Wallet 계좌",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${storedUserData?.representAccountId ?: "계좌번호 없음"}",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                // 숫자만 포함된 입력값만 업데이트
                if (newValue.all { it.isDigit() }) {
                    amount = newValue
                }
            },
            label = { Text("금액") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (amount.isNotEmpty()) {
                    IconButton(onClick = { amount = "" }) {
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


        Spacer(modifier = Modifier.weight(1f))

        BlueButton(
            onClick = {
                val accountId = storedUserData?.representAccountId ?: ""
                val amountInt = amount.toIntOrNull() ?: 0
                if (accountId.isNotEmpty() && amountInt > 0) {
                    depositViewModel.depositFunds(accountId, amountInt)
                } else {
                    println("Error: Invalid accountId or amount")
                }
            },
            text = "채우기",
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
fun MyWalletDepositScreenPreview() {
    MyWalletDepositScreen(navController = rememberNavController())
}
