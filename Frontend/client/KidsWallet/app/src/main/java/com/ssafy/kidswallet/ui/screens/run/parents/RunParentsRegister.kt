package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.ImageUtils.base64ToBitmap
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.run.viewmodel.state.StateRunViewModel
import com.ssafy.kidswallet.viewmodel.state.StateRunMoneyViewModel

@Composable
fun RunParentsRegisterScreen(
    navController: NavController,
    viewModel: StateRunViewModel = viewModel(),
    stateRunMoneyViewModel: StateRunMoneyViewModel = viewModel()
) {
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
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "달리기 이미지",
                            modifier = Modifier.size(120.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.icon_bundle),
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
                        text = viewModel.selectedDateText,
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
                imageResId = R.drawable.character_me // Replace with your image resource
            )

            Spacer(modifier = Modifier.height(8.dp))

            ParticipantInfo(
                name = "응애재훈",
                amount = "목표 " + NumberUtils.formatNumberWithCommas(stateRunMoneyViewModel.parentGoalMoney) + "원",
                imageResId = R.drawable.character_run_member // Replace with your image resource
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 신청 버튼
        BlueButton(
            onClick = {
                // 상태 초기화 호출
                stateRunMoneyViewModel.resetGoals()
                viewModel.resetRunImageText()

                // 네비게이션 이동
                navController.navigate("run") {
                    popUpTo(0) { inclusive = true }
                }
            },
            text = "신청하기",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
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
