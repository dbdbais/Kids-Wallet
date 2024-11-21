package com.ssafy.kidswallet.ui.screens.begging.mission

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
import androidx.compose.material3.Divider
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
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.GreenButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.DateUtils
import com.ssafy.kidswallet.ui.components.TopToBegging
import com.ssafy.kidswallet.viewmodel.BeggingMissionViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BeggingMissionCompleteScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopToBegging(title = "미션", navController = navController) 
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row() {
                Button(
                    onClick = { navController.navigate("beggingMissionCheck") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF7F7F7),
                        contentColor =  Color(0xFF8C8595)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(160.dp)
                        .height(50.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(text = "미션 현황", fontWeight = FontWeight.Bold, style = FontSizes.h16)
                }
                Button(
                    onClick = { navController.navigate("beggingMissionComplete") },
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
                    Text(text = "미션 완료", fontWeight = FontWeight.Bold, style = FontSizes.h16)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_flag),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp) 
                        .clip(CircleShape) 
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "미션 완료",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16,
                    color = Color(0xFF8C8595)
                )
            }
            Divider(
                color = Color(0xFF6DCEF5), 
                thickness = 2.dp, 
                modifier = Modifier.padding(vertical = 8.dp) 
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                CompleteMissionList()
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompleteMissionList(viewModel: BeggingMissionViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val userId = storedUserData?.userId

    val missionList = viewModel.missionList.collectAsState().value
    val completeMission = missionList.filter { it.mission?.missionStatus == "complete" }

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
                    } 
                }
            }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (completeMission.isEmpty()) {
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
                    text = "완료한 미션이 없어요",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top, 
                horizontalAlignment = Alignment.CenterHorizontally, 
            ) {
                items(completeMission) {mission ->
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
                            GreenButton(onClick = { /*TODO*/ }, text = "승인", height = 40)
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
                                        id = if (kotlin.random.Random.nextBoolean()) R.drawable.character_old_man else R.drawable.character_old_girl
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp) 
                                        .clip(CircleShape) 
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
                                    text = "님이 용돈을 주셨어요!",
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