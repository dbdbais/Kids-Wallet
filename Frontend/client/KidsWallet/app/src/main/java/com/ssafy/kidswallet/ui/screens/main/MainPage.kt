package com.ssafy.kidswallet.ui.screens.main

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.zIndex
import com.ssafy.kidswallet.viewmodel.AccountTransactionViewModel
import com.ssafy.kidswallet.viewmodel.UpdateUserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainPageScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel(), relationViewModel: RelationViewModel = viewModel(), updateUserViewModel: UpdateUserViewModel = viewModel(), accountTransactionViewModel: AccountTransactionViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val relationList = storedUserData?.relations
    val userNameList = relationList?.map { it.userName } ?: emptyList()
    val userId = storedUserData?.userId
    var showDialog by remember { mutableStateOf(false) }
    var backShowDialog by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }
    val updatedUserData by updateUserViewModel.updatedUserData.collectAsState()
    val accountState by accountTransactionViewModel.accountState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var isRelationRegistered by remember { mutableStateOf(false) }

    var refreshCount1 by remember { mutableStateOf(0) }
    var refreshCount2 by remember { mutableStateOf(0) }
    val maxRefreshCount = 12

    BackHandler {
        backShowDialog = true
    }

    if (backShowDialog) {
        AlertDialog(
            onDismissRequest = { backShowDialog = false },
            text = {
                Text(
                    text = "키즈월렛 앱을 종료하시겠습니까?",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 16.dp)
                )},
            containerColor = Color.White,
            confirmButton = {
                BlueButton(
                    onClick = {
                        backShowDialog = false
                        navController.context.let { context ->
                            (context as? android.app.Activity)?.finish()
                        }
                    },
                    modifier = Modifier.width(110.dp),
                    text = "예"
                )
            },
            dismissButton = {
                GrayButton(
                    onClick = { backShowDialog = false },
                    modifier = Modifier.width(150.dp),
                    text = "취소"
                )
            }
        )
    }

    LaunchedEffect(Unit) {
        storedUserData?.representAccountId?.let { accountId ->
            accountTransactionViewModel.getTransactionData(accountId)
        }
    }

    // isCardRegistered가 true로 바뀌면 updateUser 호출
    LaunchedEffect(isRelationRegistered) {
        if (isRelationRegistered && userId != null) {
            updateUserViewModel.updateUser(userId)
        }
    }

    LaunchedEffect(Unit) {
        while (refreshCount1 < maxRefreshCount) {
            storedUserData?.representAccountId?.let { accountId ->
                accountTransactionViewModel.getTransactionData(accountId)
            }
            refreshCount1++ // 실행 횟수 증가
            delay(5000) // 5초 대기
        }
    }

    LaunchedEffect(isRelationRegistered) {
        while (refreshCount2 < maxRefreshCount) {
            if (isRelationRegistered && userId != null) {
                updateUserViewModel.updateUser(userId)
            }
            refreshCount2++ // 실행 횟수 증가
            delay(5000) // 5초 대기
        }
    }

    // updateUser 성공 시 updatedUserData가 업데이트되면 실행
    LaunchedEffect(updatedUserData) {
        if (updatedUserData != null) {
            // updatedUserData가 업데이트된 후 처리
            Log.d("Hello", updatedUserData.toString())
            loginViewModel.saveUserData(updatedUserData!!)
            navController.navigate("mainPage") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Log.d("MainPageScreen", "Stored User Data: ${storedUserData?.toString() ?: "No Data"}")
    // 직접 입력을 위한 다이얼로그
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                focusManager.clearFocus()
            },
            title = { Text(text = "추가하기") },
            text = {
                Column {
                    OutlinedTextField(
                        value = input,
                        onValueChange = {
                            val filteredInput = it.filter { char -> char.isLetterOrDigit() } // 영문자와 숫자만 허용
                            if (filteredInput.length <= 15) { // 15자 입력 제한
                                input = filteredInput
                            }
                        },
                        label = { Text("아이의 ID") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true // 한 줄 제한
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
            containerColor = Color.White,
            confirmButton = {
                BlueButton(
                    onClick = {
                        if (userNameList.contains(input)) {
                            Toast.makeText(context, "이미 존재하는 이름입니다.", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        } else {
                            val relationModel = storedUserData?.userName?.let { parentName ->
                                RelationModel(
                                    childName = input,
                                    parentName = parentName
                                )
                            }
                            if (relationModel != null) {
                                relationViewModel.addRelation(relationModel) {
                                    isRelationRegistered = true
                                }
                                Log.d("relationModel", "Current input value: $relationModel")
                            }
                            input = ""
                            showDialog = false
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
                    onClick = {
                        input = ""
                        showDialog = false
                        },
                    height = 40,
                    modifier = Modifier.width(130.dp), // 원하는 너비 설정
                    elevation = 0,
                    text = "취소"
                )
            }
        )
    }

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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 코인 이미지와 금액 텍스트가 포함된 이미지
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 32.dp, start = 16.dp), // 아래쪽 여백 추가
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
                        text = accountState?.data?.firstOrNull()?.curBalance?.let {
                            "${NumberUtils.formatNumberWithCommas(it)}원" } ?: "0원",
                        fontWeight = FontWeight.W900,
                        style = FontSizes.h24,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Logout",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(50.dp) // 가로 크기 조절
                        .height(50.dp) // 세로 크기 조절
                        .zIndex(1f) // 요소를 앞으로 가져오기 위해 zIndex 사용
                        .offset(y= -5.dp)
                        .clickable {
                            navController.navigate("loginRouting") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                )
            }

            // 안내 문구 이미지 (왼쪽 정렬)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, start = 16.dp), // 아래쪽 여백 추가
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (storedUserData?.userRole == "CHILD") {
                    if (storedUserData.relations?.isEmpty() == true) {
                        Text(
                            text = "어른이 추가될 때까지 기다려보아요",
                            fontWeight = FontWeight.W900,
                            style = FontSizes.h20,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    } else {
                        Box {
                            LazyRow(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 4.dp)
                            ) {
                                items(storedUserData.relations ?: emptyList()) { relation ->
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp) // 각 항목의 외부 여백
                                            .background(
                                                color = if (relation.userGender == "MALE") Color(0xFFF4FBFE) else Color(0xFFFFF4F5),
                                                shape = RoundedCornerShape(40.dp) // 둥근 모서리 처리
                                            )
                                            .padding(8.dp) // 내부 여백
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(
                                                        color = if (relation.userGender == "MALE") Color(0xFFE9F8FE) else Color(
                                                            0xFFFFEDEF
                                                        ),
                                                        shape = CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(
                                                        id = if (relation.userGender == "MALE") R.drawable.character_old_man else R.drawable.character_old_girl
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(32.dp) // 이미지 크기 조정
                                                        .clip(CircleShape) // 이미지도 동그랗게 클립
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            relation.userName?.let {
                                                Text(
                                                    text = it,
                                                    fontWeight = FontWeight.Bold,
                                                    style = FontSizes.h16
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.icon_add),
                        contentDescription = "Waiting Message",
                        modifier = Modifier
                            .size(60.dp) // 이미지 크기 조절 (80 x 80)
                            .clickable { showDialog = true }
                    )
                    if (storedUserData?.relations?.isEmpty() == true) {
                        Text(
                            text = "아이를 추가해주세요",
                            fontWeight = FontWeight.W900,
                            style = FontSizes.h20,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    } else {
                        Box {
                            LazyRow(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 4.dp)
                            ) {
                                items(storedUserData?.relations ?: emptyList()) { relation ->
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp) // 각 항목의 외부 여백
                                            .background(
                                                color = if (relation.userGender == "MALE") Color(0xFFF4FBFE) else Color(0xFFFFF4F5),
                                                shape = RoundedCornerShape(40.dp) // 둥근 모서리 처리
                                            )
                                            .padding(8.dp) // 내부 여백
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(
                                                        color = if (relation.userGender == "MALE") Color(0xFFE9F8FE) else Color(
                                                            0xFFFFEDEF
                                                        ),
                                                        shape = CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(
                                                        id = if (relation.userGender == "MALE") R.drawable.character_young_man else R.drawable.character_young_girl
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(32.dp) // 이미지 크기 조정
                                                        .clip(CircleShape) // 이미지도 동그랗게 클립
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            relation.userName?.let {
                                                Text(
                                                    text = it,
                                                    fontWeight = FontWeight.Bold,
                                                    style = FontSizes.h16
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

            // 네 개의 아이콘 박스 (2x2 Grid)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CardApplicationBox(navController, storedUserData)
                BeggingApplicationBox(navController, storedUserData)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TogetherApplicationBox(navController, storedUserData)
                QuizApplicationBox(navController, storedUserData)
            }

            if (storedUserData?.representAccountId == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 10.dp)
                        .clickable { navController.navigate("myData") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_button),
                        contentDescription = "Account Button",
                        modifier = Modifier
                            .width(350.dp)
                            .height(200.dp)
                            .align(Alignment.Center)
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
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_button),
                        contentDescription = "Account Button",
                        modifier = Modifier
                            .width(350.dp)
                            .height(200.dp)
                            .align(Alignment.Center)
                    )
                    Text(
                        text = "주거래 통장\n${storedUserData.representAccountId}",
                        style = FontSizes.h24,
                        fontWeight = FontWeight.W900,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
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
            .size(160.dp)
            .clickable {
                if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false && storedUserData.representAccountId == null) {
                    navController.navigate("myData")
                } else if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false) {
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
            text = if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false && storedUserData.representAccountId == null) {
                "통장 신청"
            } else if (storedUserData?.userRole == "CHILD" && storedUserData.hasCard == false) {
                "카드 신청"
            } else {
                "내 지갑"
            },
            style = FontSizes.h20,
            fontWeight = FontWeight.W900,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 16.dp)
        )
    }
}

@Composable
fun BeggingApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(160.dp)
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
    }
}

@Composable
fun TogetherApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(160.dp)
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
                .padding(start = 30.dp, top = 16.dp)
        )
    }
}

@Composable
fun QuizApplicationBox(navController: NavController, storedUserData: UserDataModel?) {
    Box(
        modifier = Modifier
            .size(160.dp)
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
                .padding(start = 30.dp, top = 16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MainPageScreenPreview(navController: NavController = rememberNavController()) {
    MainPageScreen(navController = navController)
}
