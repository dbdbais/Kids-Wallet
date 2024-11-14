package com.ssafy.kidswallet.ui.screens.begging.beggingmoney

import BeggingReasonViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.BlueButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.kidswallet.viewmodel.BeggingRequestViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeggingRequestReasonScreen(
    navController: NavController,
    name: String?,
    amount: Int?,
    viewModel: BeggingReasonViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    beggingRequestViewModel: BeggingRequestViewModel = viewModel()
) {
    val textState = viewModel.textModel.collectAsState()
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value

    val relation = storedUserData?.relations
    val toUserId = relation?.find {it.userName == name}?.userId
    val userId = storedUserData?.userId

    val context = LocalContext.current

    Column(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Top(title = "조르기", navController = navController) // BackButton 사용
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$amount",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)
                )
                Text(
                    text = "원을 요청할래요",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_coin),
                contentDescription = "Coin with Amount",
                modifier = Modifier
                    .width(80.dp) // 가로 크기 조절
                    .height(80.dp) // 세로 크기 조절
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .border(3.dp, Color(0xFFB2EBF2), RoundedCornerShape(16.dp))
                    .border(6.dp, Color(0xFF99DDF8).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .border(9.dp, Color(0xFF99DDF8).copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(top = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.character_old_girl),
                        contentDescription = "Female Character",
                        modifier = Modifier
                            .size(160.dp)
                    )
                    TextField(
                        value = textState.value.text,
                        onValueChange = { newText ->
                            if (newText.length <= 20) {
                                viewModel.setText(newText)
                            }
                        },
                        placeholder = {
                            Text(
                                text = "요청 이유를 꼭 적어주세요!",
                                style = FontSizes.h16,
                                color = Color(0xFF8C8595),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Transparent)
                            .padding(horizontal = 0.dp),
                        textStyle = FontSizes.h16.copy(fontWeight = FontWeight.Bold),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF7F7F7),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            Text(
                                text = "${textState.value.text.length}/20",
                                style = FontSizes.h16,
                                color = Color.Gray
                            )
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp, start = 16.dp, end = 16.dp) // 원하는 절대 거리 설정
        ) {
            BlueButton(
//                onClick = {
//                    if (textState.value.text.isNotBlank()) {
//                        navController.navigate(
//                            "beggingRequestComplete?name=$name&amount=$amount&reason=${textState.value.text}"
//                        )
//                    }
//                },
                onClick = {
                    if (textState.value.text.isNotBlank() && userId != null && toUserId != null && amount != null) {
                        beggingRequestViewModel.sendBeggingRequest(
                            userId = userId.toString(),
                            toUserId = toUserId.toString(),
                            message = textState.value.text,
                            amount = amount,
                            onSuccess = { response ->
                                // 성공 시 처리
                                navController.navigate("beggingRequestComplete?name=$name&amount=$amount&reason=${textState.value.text}")
                            },
                            onError = { error ->
                                Toast.makeText(
                                    context,
                                    "Error occurred: ${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                },
                text = "확인",
                modifier = Modifier
                    .width(400.dp) // 원하는 너비 설정
                    .align(Alignment.BottomCenter), // 화면 하단 중앙에 정렬
                elevation = 4
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun BeggingRequestReasonScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    BeggingRequestReasonScreen(navController = navController, name = "테스트 사용자", amount = 50000) // 테스트용 이름 전달
}