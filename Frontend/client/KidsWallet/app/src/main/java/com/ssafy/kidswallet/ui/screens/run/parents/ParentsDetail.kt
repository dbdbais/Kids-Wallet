package com.ssafy.kidswallet.ui.screens.run.parents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.DdayBadge
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.ImageUtils.base64ToBitmap
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.TogetherDetailViewModel
import com.ssafy.kidswallet.viewmodel.TogetherParentsDetailRejectViewModel
import com.ssafy.kidswallet.viewmodel.TogetherParentsDetailViewModel

@Composable
fun ParentsDetailScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    togetherRunId: Int?,
    togetherDetailViewModel: TogetherDetailViewModel,
    togetherParentsDetailViewModel: TogetherParentsDetailViewModel,
    togetherParentsDetailRejectViewModel: TogetherParentsDetailRejectViewModel,
) {
    println("Received togetherRunId: $togetherRunId")
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val togetherDetail = togetherDetailViewModel.togetherDetail.collectAsState().value
    val formattedDate = togetherDetail?.expiredAt?.joinToString(separator = ".") ?: "N/A"

    // 다이얼로그 상태 관리
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showResultDialog = remember { mutableStateOf(false) }
    val resultMessage = remember { mutableStateOf("") }
    val isSuccess = remember { mutableStateOf(false) }

    // 데이터 로드
    togetherRunId?.let { id ->
        togetherDetailViewModel.fetchTogetherDetail(id)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(
            title = "같이 달리기",
            navController = navController
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6DCEF5)) // 배경색 설정
                .padding(16.dp)
                .padding(top = 30.dp, bottom = 14.dp), // 상하 패딩 추가
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 목표 이미지 및 정보 박스
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(45.dp)
                        )
                        .background(Color(0xFF99DDF8), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    // D-Day 배지를 좌측 상단에 위치
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart) // 좌측 상단 정렬
                            .padding(8.dp)
                    ) {
                        DdayBadge(remainingDays = togetherDetail?.dDay ?: 0)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Base64 이미지 변환 및 표시
                        togetherDetail?.targetImage?.let { base64Image ->
                            val bitmap = base64ToBitmap(base64Image)
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "목표 이미지",
                                    modifier = Modifier.size(250.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_labtop),
                                    contentDescription = "기본 이미지",
                                    modifier = Modifier.size(250.dp)
                                )
                            }
                        }

                        Text(
                            text = "$formattedDate 까지 " + "${NumberUtils.formatNumberWithCommas(togetherDetail?.targetAmount ?: 0)}원을 모아야 해요!",
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // 추가 정보 텍스트
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "1. 키즈월렛에서 매주 자동 이체됩니다.\n" +
                                "2. 미납 시 다음 납입일까지 해당 금액이 계좌에 있어야 하며, 다음 납입일에 2주분이 함께 출금됩니다. 만일, 다음 납입일에도 미납 시 계약이 해제됩니다.",
                        style = FontSizes.h16,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 참여자 정보
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RParticipantInfo(
                name = "${togetherDetail?.childName}",
                currentAmount = togetherDetail?.childAmount ?: 0,
                goalAmount = togetherDetail?.childGoalAmount ?: 0,
                imageResId = R.drawable.character_me
            )

            Spacer(modifier = Modifier.height(8.dp))

           RParticipantInfo(
                name = "${togetherDetail?.parentName}",
                currentAmount = togetherDetail?.parentAmount ?: 0,
                goalAmount = togetherDetail?.parentGoalAmount ?: 0,
                imageResId = R.drawable.character_run_member
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 그만하기 버튼
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp) // 원하는 절대 거리 설정
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                LightGrayButton(
                    onClick = {
                        togetherRunId?.let { id ->
                            val userId = storedUserData?.userId ?: 0 // 기본값 0을 제공하여 nullable 문제를 해결
                            togetherParentsDetailRejectViewModel.rejectTogetherRun(
                                togetherRunId = id,
                                userId = userId,
                                onSuccess = {
                                    resultMessage.value = "거절이 완료되었습니다."
                                    isSuccess.value = true
                                    showResultDialog.value = true
                                },
                                onFailure = { errorMessage ->
                                    resultMessage.value = "거절 중 오류가 발생했습니다. 다시 시도해주세요."
                                    isSuccess.value = false
                                    showResultDialog.value = true
                                    Log.e("ParentsDetailScreen", errorMessage)
                                }
                            )
                        } ?: run {
                            resultMessage.value = "유효하지 않은 ID입니다."
                            isSuccess.value = false
                            showResultDialog.value = true
                        }
                    },
                    text = "거절하기",
                    modifier = Modifier
                        .width(130.dp), // 기존 너비 유지
                    height = 50,      // 기존 높이 유지
                    elevation = 4     // 기존 elevation 유지
                )


                BlueButton(
                    onClick = {
                        togetherRunId?.let { id ->
                            // `userId`는 필요에 따라 가져와야 합니다 (예: ViewModel에서 가져오는 등)
                            val userId = storedUserData?.userId ?: 0 // 기본값 0을 제공하여 nullable 문제를 해결
                            // 예시로 임의의 userId를 사용. 실제로는 로그인 데이터 등에서 가져와야 합니다.
                            togetherParentsDetailViewModel.acceptTogetherRun(
                                togetherRunId = id,
                                userId = userId,
                                onSuccess = {
                                    resultMessage.value = "수락이 완료되었습니다."
                                    isSuccess.value = true
                                    showResultDialog.value = true
                                },
                                onFailure = { errorMessage ->
                                    resultMessage.value = "수락 중 오류가 발생했습니다. 다시 시도해주세요."
                                    isSuccess.value = false
                                    showResultDialog.value = true
                                    Log.e("ParentsDetailScreen", errorMessage)
                                }
                            )
                        } ?: run {
                            resultMessage.value = "유효하지 않은 ID입니다."
                            isSuccess.value = false
                            showResultDialog.value = true
                        }
                    },
                    text = "수락하기",
                    modifier = Modifier
                        .width(230.dp),
                    height = 50,
                    elevation = 4
                )

            }
        }
    }

    // 수락 다이얼로그
    if (showConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog.value = false },
            title = { Text("성공", fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text("거절 하시겠습니까? 이 작업은 되돌릴 수 없습니다.", fontWeight = FontWeight.Bold, color = Color(0xFF8C8595)) },
            confirmButton = {
                BlueButton(
                    onClick = {
                        showConfirmationDialog.value = false
                        togetherDetail?.savingContractId?.let { id ->
                            togetherDetailViewModel.deleteTogetherRun(
                                id,
                                onSuccess = {
                                    resultMessage.value = "실패는 성공의 어머니! 또 다른 도전을 응원합니다!"
                                    isSuccess.value = true
                                    showResultDialog.value = true
                                },
                                onFailure = { errorMessage ->
                                    resultMessage.value = "삭제 중 오류가 발생했습니다. 다시 시도해주세요."
                                    isSuccess.value = false
                                    showResultDialog.value = true
                                    Log.e("parentsDetailScreen", errorMessage)
                                }
                            )
                        }
                    },
                    text = "확인"
                )
            },
            dismissButton = {
                LightGrayButton(
                    onClick = { showConfirmationDialog.value = false },
                    text = "취소"
                )
            }
        )
    }

    // 결과 다이얼로그
    if (showResultDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showResultDialog.value = false
                if (isSuccess.value) {
                    navController.navigate("run") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            title = {
                Text(
                    text = if (isSuccess.value) "성공" else "오류",
                    color = if (isSuccess.value) Color(0xFF77DD77) else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = resultMessage.value,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8C8595)
                )
            },
            confirmButton = {
                BlueButton(
                    onClick = {
                        showResultDialog.value = false
                        if (isSuccess.value) {
                            navController.navigate("run") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    text = "확인"
                )
            }
        )
    }
}

@Composable
fun RParticipantInfo(name: String, currentAmount: Int, goalAmount: Int, imageResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "$name 이미지",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = name,
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            // 현재 금액
            Text(
                text = "현재 ${NumberUtils.formatNumberWithCommas(currentAmount)}원",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6DCEF5)
            )

            // 목표 금액
            Text(
                text = "목표 ${NumberUtils.formatNumberWithCommas(goalAmount)}원",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C8595)
            )
        }
    }
}

