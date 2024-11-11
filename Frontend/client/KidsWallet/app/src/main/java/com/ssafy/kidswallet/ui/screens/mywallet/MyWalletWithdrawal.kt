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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MyWalletWithdrawalScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize() // 전체 Column에 패딩을 주지 않습니다.
    ) {
        TopSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        FormSection(modifier = Modifier.padding(horizontal = 16.dp), navController) // FormSection에 개별 패딩 적용
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

@Composable
fun FormSection(modifier: Modifier = Modifier, navController: NavController) {
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = accountNumber,
            onValueChange = { accountNumber = it },
            label = { Text("계좌번호") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (accountNumber.isNotEmpty()) {
                    IconButton(onClick = { accountNumber = "" }) {
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
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= 15) { // 15자 이하로 제한
                    message = it
                }
            },
            label = { Text("메세지를 적어주세요!") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (message.isNotEmpty()) {
                    IconButton(onClick = { message = "" }) {
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
            onClick = { navController.navigate("runParentsMoney") },
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
fun MyWalletWithdrawalScreenPreview() {
    MyWalletWithdrawalScreen(navController = rememberNavController())
}
