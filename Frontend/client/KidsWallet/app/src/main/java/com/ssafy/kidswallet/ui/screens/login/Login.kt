package com.ssafy.kidswallet.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.R

@Composable
fun Login(navController: NavHostController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var idFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf((false)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_full),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = "로그인",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("아이디") },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> idFocused = focusState.isFocused },
            trailingIcon = {
                if (id.isNotEmpty()) {
                    IconButton(onClick = { id = ""}) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear ID",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color( 0xFFD3D0D7)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> passwordFocused = focusState.isFocused },
            trailingIcon = {
                if (password.isNotEmpty()) {
                    IconButton(onClick = { password = "" }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "Clear Password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6DCEF5),
                unfocusedBorderColor = Color( 0xFFD3D0D7),
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            onClick = { /* 로그인 로직 추가 */ },
            text = "확인"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "아직 키즈월렛 회원이 아니신가요?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "회원가입",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6DCEF5),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
    }
}

@Composable
fun GradientButton(onClick: () -> Unit, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                ),
                shape = RoundedCornerShape(15.dp) // 모서리 반경 설정
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp), // 버튼 높이 조정
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}