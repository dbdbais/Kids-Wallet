package com.ssafy.kidswallet.ui.screens.begging

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.PersonViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BeggingMoneyScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Top(title = "조르기", navController = navController) // BackButton 사용
            Spacer(modifier = Modifier.height(16.dp))
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

                Spacer(modifier = Modifier.height(16.dp))
                PeopleSelect()

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6DCEF5), // 버튼 배경색 설정
                        contentColor = Color.White // 텍스트 색상 설정
                    )
                ) {
                    Text("선택하기")
                }

                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "최근 조르기 내역",
                    fontWeight = FontWeight.Black,
                    style = FontSizes.h32
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.empty), // 이미지 리소스
                    contentDescription = "Empty Icon",
                    modifier = Modifier
                        .size(150.dp)
                        .graphicsLayer(alpha = 0.8f) // 투명도 조절
                )
            }
        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
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

@Composable
fun PeopleList() {
    Box() {

    }
}

@Composable
fun PeopleSelect(viewModel: PersonViewModel = viewModel()) {
    val peopleList = viewModel.peopleList.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(2.dp, Color(0xFFB2EBF2), RoundedCornerShape(16.dp))
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
                    thickness = 1.dp,
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
                horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬
                ) {
                    items(peopleList) { person ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = person.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = person.gender,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}