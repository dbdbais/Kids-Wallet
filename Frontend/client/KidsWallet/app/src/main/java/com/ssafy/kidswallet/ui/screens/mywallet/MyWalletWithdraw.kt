package com.ssafy.kidswallet.ui.screens.mywallet

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
import com.ssafy.kidswallet.viewmodel.AccountWithdrawViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel

@Composable
fun MyWalletWithdrawScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val withdrawViewModel: AccountWithdrawViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val withdrawSuccess by withdrawViewModel.withdrawSuccess.collectAsState()
    val withdrawError by withdrawViewModel.withdrawError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
    ) {
        WTopSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
        WHeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        WFormSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            navController = navController,
            withdrawViewModel = withdrawViewModel,
            loginViewModel = loginViewModel
        )

        // 성공 또는 오류 메시지 표시
        when {
            withdrawSuccess == true -> {
                Text(
                    text = "출금이 성공적으로 완료되었습니다!",
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
            withdrawError != null -> {
                Text(
                    text = withdrawError ?: "출금 오류가 발생했습니다.",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun WTopSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Top(title = "빼기", navController = navController)
    }
}

@Composable
fun WHeaderSection() {
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
                append(" 에서\n얼마를 뺄까요?")
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

@Composable
fun WFormSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    withdrawViewModel: AccountWithdrawViewModel,
    loginViewModel: LoginViewModel
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "내 계좌",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "싸피뱅크 991223*******",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
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
                    withdrawViewModel.withdrawFunds(accountId, amountInt)
                } else {
                    println("Error: Invalid accountId or amount")
                }
            },
            text = "빼기",
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
fun MyWalletWithdrawScreenPreview() {
    MyWalletWithdrawScreen(navController = rememberNavController())
}
