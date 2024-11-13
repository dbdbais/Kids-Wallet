package com.ssafy.kidswallet.ui.screens.begging.mission

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun BeggingMissionPlayScreen(navController: NavController, name: String, begMoney: Int, begContent: String, missionContent: String) {
    val formattedNumber = NumberUtils.formatNumberWithCommas(begMoney)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "미션 수행하기", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = name,
                    color = Color(0xFF6DCEF5),
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "님에게",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Text(
                text = "미션을 받았어요",
                style = FontSizes.h24,
                fontWeight = FontWeight.Black
            )
        }
        Column(
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "조르기 내용",
                style = FontSizes.h24,
                color = Color(0xFF6DCEF5),
                fontWeight = FontWeight.Black
            )
            Divider(
                color = Color(0xFF6DCEF5), // 원하는 색상 적용
                thickness = 2.dp, // 두께 설정 (원하는 값으로 조정 가능)
                modifier = Modifier.padding(start = 16.dp, end = 16.dp) // 여백 추가 (선택 사항)
            )
            Text(
                text = begContent,
                style = FontSizes.h20,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${formattedNumber}원",
                style = FontSizes.h20,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .width(400.dp)
                .height(110.dp)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "미션 내용",
                style = FontSizes.h24,
                color = Color(0xFF6DCEF5),
                fontWeight = FontWeight.Black
            )
            Divider(
                color = Color(0xFF6DCEF5), // 원하는 색상 적용
                thickness = 2.dp, // 두께 설정 (원하는 값으로 조정 가능)
                modifier = Modifier.padding(start = 16.dp, end = 16.dp) // 여백 추가 (선택 사항)
            )
            Text(
                text = missionContent,
                style = FontSizes.h20,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFF7F7F7), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageUri == null) {
                Text(
                    text = "미션 사진 추가하기",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.icon_plus2),
                    contentDescription = "사진 첨부",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { launcher.launch("image/*") }
                )
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "첨부된 사진",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clickable { launcher.launch("image/*") }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp, start = 16.dp, end = 16.dp) // 원하는 절대 거리 설정
        ) {
            BlueButton(
                onClick = {
                    showDialog = true
                },
                text = "보내기",
                modifier = Modifier
                    .width(400.dp) // 원하는 너비 설정
                    .align(Alignment.BottomCenter), // 화면 하단 중앙에 정렬
                height = 50,
                elevation = 4
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = name,
                            color = Color(0xFF6DCEF5),
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "님에게",
                            fontWeight = FontWeight.Black
                        )
                    }
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "미션을 보냈습니다!",
                            fontWeight = FontWeight.Black,
                            style = FontSizes.h20
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // 여백 추가
                    }
                },
                containerColor = Color.White,
                confirmButton = {
                    BlueButton(
                        onClick = {
                            showDialog = false
                            navController.navigate("begging") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.width(130.dp),
                        text = "조르기로",
                    )
                },
                dismissButton = {
                    LightGrayButton(
                        onClick = {
                            showDialog = false
                            navController.navigate("mainPage") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.width(130.dp),
                        text = "메인으로"
                    )
                }
            )
        }
    }
}