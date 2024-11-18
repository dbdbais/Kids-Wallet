package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.data.model.TogetherrunReisterModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.ImageUtils.base64ToBitmap
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.run.viewmodel.state.StateRunViewModel
import com.ssafy.kidswallet.ui.viewmodel.TogetherrunReisterViewModel
import com.ssafy.kidswallet.ui.viewmodel.TogetherrunReisterViewModelFactory
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.state.StateRunMoneyViewModel

@Composable
fun RunParentsRegisterScreen(
    navController: NavController,
    viewModel: StateRunViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    stateRunMoneyViewModel: StateRunMoneyViewModel = viewModel()
) {
    val apiService = RetrofitClient.apiService
    val factory = TogetherrunReisterViewModelFactory(apiService)
    val togetherrunReisterViewModel: TogetherrunReisterViewModel = viewModel(factory = factory)

    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value

    // AlertDialog 상태 관리
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val isSuccess = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "달리기 내용 확인",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // 메인 콘텐츠 컨테이너
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(25.dp))
                    .padding(16.dp)
                    .widthIn(max = 300.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    val bitmap = base64ToBitmap(viewModel.runImageText)
                    if (bitmap != null) {
                        println("Bitmap 생성 성공")
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "달리기 이미지",
                            modifier = Modifier.size(120.dp)
                        )
                    } else {
                        println("Bitmap 생성 실패 - Base64 문자열: ${viewModel.runImageText}")
                        Image(
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = "달리기 이미지",
                            modifier = Modifier.size(120.dp)
                        )
                    }


                    Text(
                        text = "${NumberUtils.formatNumberWithCommas(stateRunMoneyViewModel.togetherGoalMoney)}원 달리기",
                        color = Color(0xFF6DCEF5),
                        style = FontSizes.h24,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // 목표 텍스트
                    Text(
                        text = viewModel.goalText,
                        style = FontSizes.h20,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // 날짜 텍스트
                    Text(
                        text = viewModel.selectedDateText + "까지 \n" + "멤버와 함께 목표를 달성하세요",
                        style = FontSizes.h16,
                        color = Color(0xFF8C8595),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Members list
        Column(modifier = Modifier.fillMaxWidth()) {
            ParticipantInfo(
                name = "나",
                amount = "목표 " + NumberUtils.formatNumberWithCommas(stateRunMoneyViewModel.childGoalMoney) + "원",
                imageResId = R.drawable.character_me
            )

            Spacer(modifier = Modifier.height(8.dp))

            ParticipantInfo(
                name = "응애재훈",
                amount = "목표 " + NumberUtils.formatNumberWithCommas(stateRunMoneyViewModel.parentGoalMoney) + "원",
                imageResId = R.drawable.character_run_member
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 신청 버튼
        BlueButton(
            onClick = {
                println("신청 버튼 클릭 후 로직 실행 시작") // 디버깅 로그

                // 필요한 데이터 추출
                val targetTitle = viewModel.goalText
                val targetImage = viewModel.runImageText
                val targetAmount = stateRunMoneyViewModel.togetherGoalMoney
                val targetDate = viewModel.selectedDateText
                val parentsContribute = stateRunMoneyViewModel.parentGoalMoney
                val childContribute = stateRunMoneyViewModel.childGoalMoney

                val parentsId = storedUserData?.relations?.getOrNull(0)?.userId ?: 0
                val childId = storedUserData?.userId ?: 0

                val requestModel = TogetherrunReisterModel(
                    parentsId = parentsId,
                    childId = childId,
                    targetTitle = targetTitle,
                    targetAmount = targetAmount,
                    targetDate = targetDate,
                    parentsContribute = parentsContribute,
                    childContribute = childContribute,
                    targetImage = targetImage,
                )

                // Log each extracted value
                println("Extracted Values:")
                println("targetTitle: $targetTitle")
                println("targetImage: $targetImage") // Important for base64 string
                println("targetAmount: $targetAmount")
                println("targetDate: $targetDate")
                println("parentsContribute: $parentsContribute")
                println("childContribute: $childContribute")

                println("Request Model: $requestModel") // 생성된 요청 데이터 디버깅

                togetherrunReisterViewModel.registerTogetherrun(requestModel) { success ->
                    if (success) {
                        dialogMessage.value = "등록이 성공적으로 완료되었습니다!"
                        isSuccess.value = true
                    } else {
                        dialogMessage.value = "등록 중 오류가 발생했습니다. 다시 시도해주세요."
                        isSuccess.value = false
                    }
                    showDialog.value = true
                }
            },
            text = "신청하기",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }

    // 성공 및 실패 메시지를 위한 AlertDialog
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                if (isSuccess.value) {
                    navController.navigate("run") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            title = {
                Text(
                    text = if (isSuccess.value) "성공" else "오류",
                    color = if (isSuccess.value) Color(0xFF77DD77) else Color.Red,
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
                        if (isSuccess.value) {
                            navController.navigate("run") {
                                popUpTo(0) { inclusive = true }
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


@Composable
fun ParticipantInfo(name: String, amount: String, imageResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "$name 이미지",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = name,
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = amount,
            style = FontSizes.h16,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8C8595)
        )
    }
}
