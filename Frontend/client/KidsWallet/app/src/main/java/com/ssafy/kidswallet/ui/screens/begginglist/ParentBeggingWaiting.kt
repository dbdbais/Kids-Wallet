package com.ssafy.kidswallet.ui.screens.begginglist

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.YellowButton
import com.ssafy.kidswallet.ui.components.DateUtils
import com.ssafy.kidswallet.viewmodel.BeggingMissionViewModel

@Composable
fun ParentBeggingWaitingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Top(title = "조르기 요청 내역", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row() {
                Button(
                    onClick = { navController.navigate("parentBeggingWaiting") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6DCEF5),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(text = "조르기 요청 대기", fontWeight = FontWeight.Bold, style = FontSizes.h16)
                }
                Button(
                    onClick = { navController.navigate("parentBeggingComplete") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF7F7F7),
                        contentColor = Color(0xFF8C8595)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(text = "조르기 요청 완료", fontWeight = FontWeight.Bold, style = FontSizes.h16)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            WaitingMissionList(navController = navController)
        }
    }
}

@Composable
fun WaitingMissionList(viewModel: BeggingMissionViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.fetchMissionList()
    }
    val missionList = viewModel.missionList.collectAsState().value
    val ongoingMission = missionList.filter {
        // 대기중인 것(보기)
        (it.begDto.begAccept == true && it.mission == null) ||
        (it.begDto.begAccept == null && it.mission == null) ||
        // 아이가 미션을 진행 중인 것(아직 수행X)(미션 진행 중)
        (it.mission?.missionStatus == "proceed") ||
        // 아이가 미션을 수행하고 허락을 기다리는 것(미션 확인)
        (it.mission?.missionStatus == "submit")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (ongoingMission.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.empty), // 이미지 리소스
                contentDescription = "Empty Icon",
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer(alpha = 0.8f) // 투명도 조절
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top, // 세로 중앙 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙 정렬
            ) {
                items(ongoingMission) {mission ->
                    val formattedDate = DateUtils.formatDate(mission.begDto.createAt)

                    Column (
                        modifier = Modifier
                            .width(400.dp)
                            .height(140.dp)
                            .padding(bottom = 16.dp)
                            .border(
                                6.dp,
                                Color(0xFF99DDF8).copy(alpha = 0.1f),
                                RoundedCornerShape(24.dp)
                            )
                            .border(
                                4.dp,
                                Color(0xFF99DDF8).copy(alpha = 0.3f),
                                RoundedCornerShape(24.dp)
                            )
                            .border(2.dp, Color(0xFF99DDF8), RoundedCornerShape(24.dp)),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, bottom = 8.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formattedDate,
                                fontWeight = FontWeight.Bold,
                                style = FontSizes.h16,
                                color = Color.Gray
                            )
                            if (
                                (mission.begDto.begAccept == true && mission.mission == null) ||
                                (mission.begDto.begAccept == null && mission.mission == null)
                            ) {
                                GrayButton(
                                    onClick = {

                                    },
                                    text = "미션 진행 중",
                                    height = 40
                                )
                            } else if (
                                (mission.mission?.missionStatus == "proceed")
                            ) {
                                BlueButton(
                                    onClick = {
                                        navController.navigate("parentBeggingRequestCheck/${mission.name}/${mission.begDto.begMoney}/${mission.begDto.begContent}")
                                    },
                                    text = "요청 확인",
                                    height = 40
                                )
                            } else {
                                YellowButton(
                                    onClick = {
                                        navController.navigate("parentBeggingTestMission/${mission.name}/${mission.begDto.begMoney}/${mission.begDto.begContent}/${mission.mission?.missionContent}/${mission.mission?.completionPhoto}")
                                    },
                                    text = "미션 확인",
                                    height = 40
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, bottom = 8.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (kotlin.random.Random.nextBoolean()) Color(0xFFE9F8FE) else Color(0xFFFFEDEF),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (kotlin.random.Random.nextBoolean()) R.drawable.character_young_man else R.drawable.character_young_girl
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp) // 이미지 크기 조정
                                        .clip(CircleShape) // 이미지도 동그랗게 클립
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = mission.name,
                                    fontWeight = FontWeight.Bold,
                                    style = FontSizes.h16,
                                    color = Color(0xFF6DCEF5)

                                )
                                Text(
                                    text = "님이 용돈을 요청했어요!",
                                    fontWeight = FontWeight.Bold,
                                    style = FontSizes.h16,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}