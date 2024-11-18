package com.ssafy.kidswallet.ui.screens.begginglist

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
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
import com.ssafy.kidswallet.ui.components.TopToBegging
import com.ssafy.kidswallet.viewmodel.BeggingMissionViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.state.StateBeggingMissionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParentBeggingWaitingScreen(navController: NavController, viewModel: StateBeggingMissionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopToBegging(title = "조르기 요청 내역", navController = navController) // BackButton 사용
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
                        .width(160.dp)
                        .height(50.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(text = "조르기 요청 대기", fontWeight = FontWeight.Bold, style = FontSizes.h12)
                }
                Button(
                    onClick = { navController.navigate("parentBeggingComplete") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF7F7F7),
                        contentColor = Color(0xFF8C8595)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(160.dp)
                        .height(50.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(text = "조르기 요청 완료", fontWeight = FontWeight.Bold, style = FontSizes.h12)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            WaitingMissionList(navController = navController, stateViewModel = viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaitingMissionList(viewModel: BeggingMissionViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel(), navController: NavController, stateViewModel: StateBeggingMissionViewModel) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val userId = storedUserData?.userId
    val missionList = viewModel.missionList.collectAsState().value
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (userId != null) {
            viewModel.fetchMissionList(userId = userId, reset = true)
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == missionList.size - 1) {
                    if (userId != null) {
                        viewModel.fetchMissionList(userId = userId)
                    } // 사용자 ID는 실제 데이터에 맞게 설정하세요
                }
            }
    }

    val ongoingMission = missionList.filter {
        // 대기중인 것(보기)
        (it.begDto.begAccept == true && it.mission?.missionStatus == null) ||
        (it.begDto.begAccept == null && it.mission?.missionStatus == null) ||
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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_no_transaction),
                    contentDescription = "조르기 내역 없음",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "요청 내역이 없어요",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top, // 세로 중앙 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙 정렬
            ) {
                items(ongoingMission) {mission ->
                    val formattedDate = "${mission.begDto.createAt[0]}.${mission.begDto.createAt[1]}.${mission.begDto.createAt[2]}"

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
                                (mission.begDto.begAccept == true && mission.mission?.missionStatus == null) ||
                                (mission.begDto.begAccept == null && mission.mission?.missionStatus == null)
                            ) {
                                BlueButton(
                                    onClick = {
                                        navController.navigate("parentBeggingRequestCheck/${mission.begDto.begId}/${mission.name}/${mission.begDto.begMoney}/${mission.begDto.begContent}")
                                    },
                                    text = "요청 확인",
                                    height = 40
                                )
                            } else if (
                                (mission.mission?.missionStatus == "proceed")
                            ) {
                                GrayButton(
                                    onClick = {

                                    },
                                    text = "미션 진행 중",
                                    height = 40
                                )
                            } else {
                                YellowButton(
                                    onClick = {
                                        stateViewModel.setBase64Text(mission.mission?.completionPhoto ?: "")
                                        navController.navigate("parentBeggingTestMission/${mission.mission?.missionId}/${mission.name}/${mission.begDto.begMoney}/${mission.begDto.begContent}/${mission.mission?.missionContent}")
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
                                        color = if (kotlin.random.Random.nextBoolean()) Color(
                                            0xFFE9F8FE
                                        ) else Color(0xFFFFEDEF),
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