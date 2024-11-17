package com.ssafy.kidswallet.ui.screens.regularlist

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.YellowButton
import com.ssafy.kidswallet.ui.screens.begging.mission.CurrentMissionList
import com.ssafy.kidswallet.ui.screens.begging.mission.WaitingMissionList
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import kotlin.random.Random

@Composable
fun RegularListScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val userName = storedUserData?.userName
    val userGender = storedUserData?.userGender
    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Top(title = "나의 정기 지출 목록", navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(
                        color = if (userGender == "MALE") Color(0xFFE9F8FE) else Color(
                            0xFFFFEDEF
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (userGender == "MALE") R.drawable.character_young_man else R.drawable.character_young_girl
                    ),
                    contentDescription = "Character",
                    modifier = Modifier
                        .size(180.dp) // 이미지 크기 조정
                        .clip(CircleShape) // 이미지도 동그랗게 클립
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(30.dp),
                    )
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(30.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(
                            id = R.drawable.icon_wallet
                        ),
                        contentDescription = "Wallet Icon",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "장유성",
                            style = FontSizes.h24,
                            color = Color(0xFF6DCEF5),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "님은",
                            style = FontSizes.h20,
                            color = Color(0xFF8C8595),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "매 주",
                            style = FontSizes.h20,
                            color = Color(0xFF8C8595),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "4400",
                            style = FontSizes.h24,
                            color = Color(0xFF3290FF),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "원을 내고 있어요",
                            style = FontSizes.h20,
                            color = Color(0xFF8C8595),
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo_chat),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp) // 이미지 크기 조정
                            .clip(CircleShape) // 이미지도 동그랗게 클립
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "진행중인 같이 달리기",
                        fontWeight = FontWeight.Bold,
                        style = FontSizes.h16,
                        color = Color(0xFF8C8595)
                    )
                }
                Divider(
                    color = Color(0xFF6DCEF5), // 원하는 색상 적용
                    thickness = 2.dp, // 두께 설정 (원하는 값으로 조정 가능)
                    modifier = Modifier.padding(vertical = 8.dp) // 여백 추가 (선택 사항)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Together1List()
                }
            }
        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

@Composable
fun Together1List() {
    val togetherList = listOf(
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
        mapOf(
            "date" to "2024.08.10~2024.10.24",
            "day" to "월요일",
            "amount" to 10000,
        ),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
         horizontalArrangement = Arrangement.Center,
         verticalAlignment = Alignment.CenterVertically
    ) {
        if (togetherList.isEmpty()) {
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
                    text = "같이 달리기 내역이 없어요",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 50.dp),
                 verticalArrangement = Arrangement.Top, // 세로 중앙 정렬
                 horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙 정렬
            ) {
                items(togetherList) {together ->
                    // val formattedDate = "${together[""]}.${mission.begDto.createAt[1]}.${mission.begDto.createAt[2]}"
                    // val formattedNumber = NumberUtils.formatNumberWithCommas(number)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = together["date"] as? String ?: "N/A",
                                color = Color(0xFF8C8595),
                                style = FontSizes.h12,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                Text(
                                    text = together["day"] as? String ?: "N/A",
                                    color = Color(0xFF3290FF),
                                    style = FontSizes.h16,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = " 마다 결제",
                                    color = Color.Black,
                                    style = FontSizes.h16,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = (together["amount"] as? Int)?.toString() ?: "N/A",
                            color = Color(0xFF3290FF),
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
