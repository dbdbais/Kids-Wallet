package com.ssafy.kidswallet.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes

@Composable
fun Login(navController: NavHostController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var idFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf((false)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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

        BlueButton(
            onClick = { navController.navigate("mainPage") },
            text = "확인",
            modifier = Modifier.width(400.dp), // 원하는 너비 설정
            height = 40,
            elevation = 0
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "아직 키즈월렛 회원이 아니신가요?",
            style = FontSizes.h16,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "회원가입",
            style = FontSizes.h16,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6DCEF5),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
    }
}

