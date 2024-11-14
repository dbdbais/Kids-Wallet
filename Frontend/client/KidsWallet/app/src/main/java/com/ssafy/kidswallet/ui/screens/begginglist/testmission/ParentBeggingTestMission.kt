package com.ssafy.kidswallet.ui.screens.begginglist.testmission

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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.ImageUtils.base64ToBitmap
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.PlayMissionViewModel
import com.ssafy.kidswallet.viewmodel.TestMissionViewModel
import com.ssafy.kidswallet.viewmodel.state.StateBeggingMissionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentBeggingTestMissionScreen(navController: NavController, id: Int, name: String, begMoney: Int, begContent: String, missionContent: String, testMissionViewModel: TestMissionViewModel = viewModel(), viewModel: StateBeggingMissionViewModel) {
    val formattedNumber = NumberUtils.formatNumberWithCommas(begMoney)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "미션 확인", navController = navController) // BackButton 사용
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
            val bitmap = base64ToBitmap(viewModel.imageText)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "미션 이미지",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit // 필요에 따라 조정 가능
                )
            } else {
                Text(text = "이미지를 불러올 수 없습니다.")
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 54.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LightGrayButton(
                    onClick = {
                        testMissionViewModel.testMission(missionId = id, isComplete = false)
                        navController.navigate("parentBeggingWaiting")
                    },
                    text = "거절하기",
                    modifier = Modifier
                        .width(130.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
                BlueButton(
                    onClick = {
                        testMissionViewModel.testMission(missionId = id, isComplete = true)
                        navController.navigate("parentBeggingTestComplete/${id}/${name}/${begMoney}/${begContent}")
                    },
                    text = "보내기",
                    modifier = Modifier
                        .width(230.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
            }
        }
    }
}