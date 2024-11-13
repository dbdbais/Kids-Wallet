package com.ssafy.kidswallet.ui.screens.card

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.MakeCardViewModel
import com.ssafy.kidswallet.viewmodel.UpdateUserViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun Card3Screen(navController: NavController, loginViewModel: LoginViewModel = viewModel(), makeCardViewModel: MakeCardViewModel = viewModel(), updateUserViewModel: UpdateUserViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val updatedUserData by updateUserViewModel.updatedUserData.collectAsState()

    val userId = storedUserData?.userId

    var isCardRegistered by remember { mutableStateOf(false) }

    // isCardRegistered가 true로 바뀌면 updateUser 호출
    LaunchedEffect(isCardRegistered) {
        if (isCardRegistered && userId != null) {
            updateUserViewModel.updateUser(userId)
        }
    }

    // updateUser 성공 시 updatedUserData가 업데이트되면 실행
    LaunchedEffect(updatedUserData) {
        if (updatedUserData != null) {
            // updatedUserData가 업데이트된 후 처리
            Log.d("Hello", updatedUserData.toString())
            loginViewModel.saveUserData(updatedUserData!!)
            navController.navigate("mainPage") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Top(title = "카드", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        // 진행바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFF6DCEF5), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF6DCEF5),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFF6DCEF5), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF6DCEF5),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFF6DCEF5), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF6DCEF5),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        // 카드
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_box),
                contentDescription = "택배",
                modifier = Modifier
                    .size(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 텍스트
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Column 내부 요소를 가운데 정렬
        ) {
            Text(
                text = "멋진 선택이에요!\n선택한 카드를\n빨리 보내드릴게요",
                style = FontSizes.h32,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center // 텍스트 줄바꿈 시 중앙 정렬
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 버튼
        BlueButton(
            onClick = {
                if (userId != null) {
                    // 카드 등록 완료 후, isCardRegistered를 true로 변경하여 업데이트 로직을 트리거
                    makeCardViewModel.registerCard(userId) {
                        isCardRegistered = true // 카드를 등록한 후 업데이트를 수행
                    }
                }
            },
            text = "돌아가기",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun Card3ScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    Card3Screen(navController = navController)
}