package com.ssafy.kidswallet.ui.screens.begging.beggingmoney

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.PersonViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.kidswallet.data.model.PersonModel
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.DateUtils
import com.ssafy.kidswallet.viewmodel.BeggingMissionViewModel

@Composable
fun BeggingMoneyScreen(navController: NavController ,viewModel: PersonViewModel = viewModel()) {
    val selectedPerson by viewModel.selectedPerson.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Top(title = "조르기", navController = navController) // BackButton 사용
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "용돈 조르기",
                    fontWeight = FontWeight.Black,
                    style = FontSizes.h32
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                        .height(2.dp),
                    color = Color(0xFFB2EBF2)
                )
                Spacer(modifier = Modifier.height(16.dp))

                PeopleSelect(onPersonSelected = { person ->
                    viewModel.setSelectedPerson(person)
                })

                Spacer(modifier = Modifier.height(16.dp))

                BlueButton(
                    onClick = {
                        selectedPerson?.let { person ->
                            navController.navigate("beggingRequest?name=${person.name}")
                        }
                    },
                    modifier = Modifier.width(400.dp), // 원하는 너비 설정
                    height = 50,
                    text = "선택하기",
                    elevation = 0
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "최근 조르기 내역",
                    fontWeight = FontWeight.Black,
                    style = FontSizes.h32
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                        .height(2.dp),
                    color = Color(0xFFB2EBF2)
                )
                Spacer(modifier = Modifier.height(16.dp))

                RecentlyList()
            }
        }
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter), navController
        )
    }
}

@Composable
fun PeopleSelect(
    viewModel: PersonViewModel = viewModel(),
    onPersonSelected: (PersonModel) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPeople()
    }

    val peopleList = viewModel.peopleList.collectAsState().value
    var selectedPerson by remember { mutableStateOf<PersonModel?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(3.dp, Color(0xFFB2EBF2), RoundedCornerShape(16.dp))
            .border(6.dp, Color(0xFF99DDF8).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .border(9.dp, Color(0xFF99DDF8).copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(top = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // 상단 텍스트와 밑줄
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "조르기 대상",
                    fontWeight = FontWeight.Black,
                    style = FontSizes.h20,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color(0xFFB2EBF2),
                    thickness = 2.dp,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) // 밑줄 추가
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (peopleList.isEmpty()) {
                // 리스트가 비어 있을 때 표시할 콘텐츠
                Text(
                    text = "조를 어른이 없어요",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    style = FontSizes.h20,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.character_old_girl),
                        contentDescription = "Female Character",
                        modifier = Modifier
                            .size(150.dp)
                            .graphicsLayer(alpha = 0.5f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.character_old_man),
                        contentDescription = "Male Character",
                        modifier = Modifier
                            .size(150.dp)
                            .graphicsLayer(alpha = 0.5f)
                    )
                }
            } else {
                // 리스트가 비어있지 않을 때 LazyColumn으로 나열
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top, // 세로 중앙 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙 정렬
                ) {
                    items(peopleList) { person ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (selectedPerson == person) Color(0xFFE3F2FD) else Color.Transparent,
                                    shape = RoundedCornerShape(80.dp)
                                )
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedPerson = person
                                    onPersonSelected(person)
                                }
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = if (person.gender == "남") Color(0xFFE9F8FE) else Color(
                                                0xFFFFEDEF
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = if (person.gender == "남") R.drawable.character_old_man else R.drawable.character_old_girl
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(32.dp) // 이미지 크기 조정
                                            .clip(CircleShape) // 이미지도 동그랗게 클립
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = person.name,
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

@Composable
fun RecentlyList(viewModel: BeggingMissionViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchMissionList()
    }
    // ViewModel의 데이터를 관찰
    val missionList = viewModel.missionList.collectAsState().value
    val completeMission = missionList.filter { it.mission?.missionStatus == "complete" }

    if (completeMission.isEmpty()) {
        // 데이터가 비어 있을 때는 Empty 이미지를 표시
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty), // 이미지 리소스
                contentDescription = "Empty Icon",
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer(alpha = 0.8f) // 투명도 조절
            )
        }
    } else {
        // 데이터가 있을 때는 가로로 아이템들을 나열
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // 항목 간 간격
        ) {
            items(completeMission) { mission ->
                val formattedDate = DateUtils.formatDate(mission.begDto.createAt)
                val formattedNumber = NumberUtils.formatNumberWithCommas(mission.begDto.begMoney)
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .height(160.dp)
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
                        .border(2.dp, Color(0xFF99DDF8), RoundedCornerShape(24.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                         modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = formattedDate,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = mission.name,
                                color = Color(0xFF6DCEF5),
                                fontWeight = FontWeight.Bold,
                                style = FontSizes.h24
                            )
                            Text(
                                text = "에게 조르기로",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = formattedNumber,
                                color = Color(0xFF6DCEF5),
                                fontWeight = FontWeight.Bold,
                                style = FontSizes.h24
                            )
                            Text(
                                text = "원을 받았어요!",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun BeggingMoneyScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    BeggingMoneyScreen(navController = navController)
}