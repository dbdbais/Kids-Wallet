package com.ssafy.kidswallet.ui.screens.begginglist.assignmission

import BeggingReasonViewModel
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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.kidswallet.data.model.GiveMissionModel
import com.ssafy.kidswallet.viewmodel.GiveMissionViewModel
import com.ssafy.kidswallet.viewmodel.HandleMissionViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentBeggingAssignMissionScreen(
    navController: NavController,
    id: Int,
    name: String?,
    begMoney: Int,
    begContent: String?,
    viewModel: BeggingReasonViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    handleMissionViewModel: HandleMissionViewModel = viewModel(),
    giveMissionViewModel: GiveMissionViewModel = viewModel(),

    ) {
    val textState = viewModel.textModel.collectAsState()

    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val relation = storedUserData?.relations
    val begId = relation?.find {it.userName == name}?.userId

    Column (
        modifier = Modifier
    ){
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Top(title = "미션 주기", navController = navController)
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
                    text = name ?: "알 수 없음",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)
                )
                Text(
                    text = "님은",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "무엇을 해야할까요?",
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
                    .height(290.dp)
                    .border(3.dp, Color(0xFFB2EBF2), RoundedCornerShape(16.dp))
                    .border(6.dp, Color(0xFF99DDF8).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .border(9.dp, Color(0xFF99DDF8).copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(top = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = begContent ?: "알 수 없음",
                        style = FontSizes.h20,
                        fontWeight = FontWeight.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$begMoney",
                            style = FontSizes.h24,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF6DCEF5)
                        )
                        Text(
                            text = "원이 필요해요!",
                            style = FontSizes.h20,
                            fontWeight = FontWeight.Black
                        )
                    }
                    TextField(
                        value = textState.value.text,
                        onValueChange = { newText ->
                            if (newText.length <= 20) {
                                viewModel.setText(newText)
                            }
                        },
                        placeholder = {
                            Text(
                                text = "미션 내용을 꼭 적어주세요!",
                                style = FontSizes.h16,
                                color = Color(0xFF8C8595),
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight(0.4f)
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
                                color = Color.Gray,
                            )
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp) // 원하는 절대 거리 설정
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                LightGrayButton(
                    onClick = {
                        navController.navigate("parentBeggingWaiting")
                    },
                    text = "돌아가기",
                    modifier = Modifier
                        .width(130.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
                BlueButton(
                    onClick = {
                        if (textState.value.text.isNotBlank()) {
                            handleMissionViewModel.acceptMission(begId = id)
                            giveMissionViewModel.sendMission(
                                begId = id,
                                missionMessage = textState.value.text
                            )
                            navController.navigate("parentBeggingCompleteMission/${id}/${name}/${begMoney}/${begContent}/${textState.value.text}")
                        }
                    },
                    text = "미션 보내기",
                    modifier = Modifier
                        .width(230.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
            }
        }
    }
}