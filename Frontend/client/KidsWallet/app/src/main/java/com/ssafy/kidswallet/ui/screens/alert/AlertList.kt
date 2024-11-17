package com.ssafy.kidswallet.ui.screens.alert

import android.util.Log
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.DateUtils
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.YellowButton
import com.ssafy.kidswallet.viewmodel.BeggingMissionViewModel
import com.ssafy.kidswallet.viewmodel.DeleteNoticeViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.NoticeListViewModel
import kotlin.random.Random

@Composable
fun AlertListScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Top(title = "알림", navController = navController) // BackButton 사용
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
            ) {
                AlertListBox(navController = navController)
            }
    }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

@Composable
fun AlertListBox(viewModel: NoticeListViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel(), deleteNoticeViewModel: DeleteNoticeViewModel = viewModel(), navController: NavController) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val userId = storedUserData?.userId

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.fetchNotice(it.toString())
        }
    }

    val noticeMessage = viewModel.noticeMessage.collectAsState().value
    val isNoticeDeleted = deleteNoticeViewModel.isNoticeDeleted.collectAsState().value

    LaunchedEffect(isNoticeDeleted) {
        if (isNoticeDeleted) {
            userId?.let {
                viewModel.fetchNotice(it.toString())
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
        if (noticeMessage.isEmpty()) {
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
                    text = "알림 내역이 없어요",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.Top, // 세로 중앙 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙 정렬
            ) {
                itemsIndexed(noticeMessage) { index, notice ->
                    val noticeMessage = notice.message
                    val extractedMessage = Regex("\\((.*?)\\)").find(noticeMessage)?.groupValues?.get(1) ?: ""

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
                                text = "미확인 알람",
                                fontWeight = FontWeight.Bold,
                                style = FontSizes.h16,
                                color = Color.Gray
                            )
                            BlueButton(
                                onClick = {
                                    userId?.let {
                                        deleteNoticeViewModel.deleteNotice(it.toString(), index.toString())
                                    }
                                },
                                text = "확인"
                            )
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
                                        color = if (Random.nextBoolean()) Color(0xFFE9F8FE) else Color(
                                            0xFFFFEDEF
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (Random.nextBoolean()) R.drawable.character_young_man else R.drawable.character_young_girl
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
                                    text = extractedMessage,
                                    fontWeight = FontWeight.Bold,
                                    style = FontSizes.h16,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}