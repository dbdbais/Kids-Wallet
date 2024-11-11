package com.ssafy.kidswallet.ui.screens.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.Top
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
import com.ssafy.kidswallet.data.model.RelationModel
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.viewmodel.RelationViewModel

@Composable
fun MainPageScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel(), relationViewModel: RelationViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }

    // 직접 입력을 위한 다이얼로그
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "추가하기") },
            text = {
                Column {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("아이의 ID") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            containerColor = Color.White,
            confirmButton = {
                BlueButton(
                    onClick = {
                        val relationModel = storedUserData?.userName?.let { parentName ->
                            RelationModel(
                                childName = input,
                                parentName = parentName
                            )
                        }
                        if (relationModel != null) {
                            relationViewModel.addRelation(relationModel)
                            Log.d("relationModel", "Current input value: $relationModel")
                        }
                    },
                    height = 40,
                    modifier = Modifier.width(130.dp), // 원하는 너비 설정
                    elevation = 0,
                    text = "확인"
                )
            },
            dismissButton = {
                GrayButton(
                    onClick = { showDialog = false },
                    height = 40,
                    modifier = Modifier.width(130.dp), // 원하는 너비 설정
                    elevation = 0,
                    text = "취소"
                )
            }
        )
    }

    Log.d("DataStore", "Stored user data!!: $storedUserData")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main Background",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .offset(y = (-135).dp)
        )

        // 콘텐츠 레이아웃
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 코인 이미지와 금액 텍스트가 포함된 이미지
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp), // 아래쪽 여백 추가
                horizontalArrangement = Arrangement.Start, // Row 내부 요소를 왼쪽으로 정렬
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_coin),
                    contentDescription = "Coin with Amount",
                    modifier = Modifier
                        .width(50.dp) // 가로 크기 조절
                        .height(50.dp) // 세로 크기 조절
                )

                Text(
                    text = storedUserData?.userMoney?.toString() ?: "알 수 없음",
                    fontWeight = FontWeight.W900,
                    style = FontSizes.h24,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 안내 문구 이미지 (왼쪽 정렬)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp), // 아래쪽 여백 추가
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (storedUserData?.userRole == "CHILD") {
                    Text(
                        text = "어른이 추가될 때까지 기다려보아요",
                        fontWeight = FontWeight.W900,
                        style = FontSizes.h20,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.icon_add),
                        contentDescription = "Waiting Message",
                        modifier = Modifier
                            .size(60.dp) // 이미지 크기 조절 (80 x 80)
                            .clickable { showDialog = true }
                    )
                    Text(
                        text = "아이를 추가해주세요",
                        fontWeight = FontWeight.W900,
                        style = FontSizes.h20,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // 네 개의 아이콘 박스 (2x2 Grid)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CardApplicationBox(navController, storedUserData)
                BeggingApplicationBox(navController, storedUserData)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TogetherApplicationBox(navController, storedUserData)
                QuizApplicationBox(navController, storedUserData)
            }

            // 임시 카드 접근 코드
            Box(
                modifier = Modifier
                    .clickable { navController.navigate("card") }
            ) {
                Text(
                    text = "카드",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.W900,
                    color = Color.Black,
                )
            }
            // 여기까지
            
            Spacer(modifier = Modifier.height(40.dp))
            if (storedUserData?.representAccountId == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .clickable { navController.navigate("makeAccount") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_button),
                        contentDescription = "Account Button",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Text(
                        text = "통장 개설하기",
                        style = FontSizes.h24,
                        fontWeight = FontWeight.W900,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_button),
                        contentDescription = "Account Button",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "주거래 통장",
                            style = FontSizes.h24,
                            fontWeight = FontWeight.W900,
                            color = Color.White,
                            modifier = Modifier
                        )
                        Text(
                            text = storedUserData.representAccountId,
                            style = FontSizes.h24,
                            fontWeight = FontWeight.W900,
                            color = Color.White,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

@Composable
fun CardApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clickable {
                if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false) {
                    navController.navigate("card")
                } else {
                    navController.navigate("myWallet")
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.blue_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false) {
                "카드 신청"
            } else {
                "내 지갑"
            },
            style = FontSizes.h20,
            fontWeight = FontWeight.W900,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.icon_wallet),
            contentDescription = "Wallet Icon",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun BeggingApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clickable {
                if (storedUserData?.userRole == "CHILD") {
                    navController.navigate("begging")
                } else {
                    navController.navigate("parentBeggingWaiting")
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.pink_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = if (storedUserData?.userRole == "CHILD") {
                "용돈 조르기"
            } else {
                "조르기 내역"
            },
            style = FontSizes.h20,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 32.dp, top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo_together),
            contentDescription = "Together Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun TogetherApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clickable { navController.navigate("run") },
    ) {

        Image(
            painter = painterResource(id = R.drawable.yellow_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "행복 달리기",
            style = FontSizes.h20,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )


        Image(
            painter = painterResource(id = R.drawable.logo_together2),
            contentDescription = "Together2 Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun QuizApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clickable { navController.navigate("quiz") }
    ) {

        Image(
            painter = painterResource(id = R.drawable.green_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "퀴즈",
            style = FontSizes.h20,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_quiz),
            contentDescription = "Quiz Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp)
                .offset(y = (-20).dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MainPageScreenPreview() {
    MainPageScreen(navController = rememberNavController())
}