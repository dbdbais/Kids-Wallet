package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightBlueButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.begging.CircularSlider
import com.ssafy.kidswallet.viewmodel.RunParentsAmountViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun RunParentsMoneyScreen(navController: NavController, viewModel: RunParentsAmountViewModel = viewModel()) {
    val amount by viewModel.amount.collectAsState()

    // 각각의 금액 행에 대한 편집 상태 관리
    var isEditingFirstAmount by remember { mutableStateOf(false) }
    var isEditingSecondAmount by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // 외부 클릭 시 모든 편집 종료
                    isEditingFirstAmount = false
                    isEditingSecondAmount = false
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "목표 금액 설정",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        // CircularSlider 추가
        CircularSlider(
            amount = amount,
            onAmountChange = { newAmount -> viewModel.setAmount(newAmount) }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 같이 달리기 멤버
        Text(
            text = " ",
            style = FontSizes.h20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character_me),
                    contentDescription = "나",
                    modifier = Modifier
                        .size(60.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "나",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }

            EditableAmountRow(
                initialAmount = "12500",
                onAmountChange = { newAmount -> },
                isEditing = isEditingFirstAmount,
                onEditingChange = { isEditingFirstAmount = it }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character_run_member),
                    contentDescription = "응애재훈",
                    modifier = Modifier
                        .size(55.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "응애재훈",
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }

            EditableAmountRow(
                initialAmount = "12500",
                onAmountChange = { newAmount -> },
                isEditing = isEditingSecondAmount,
                onEditingChange = { isEditingSecondAmount = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 멤버 선택 버튼
        LightBlueButton(
            onClick = { navController.navigate("runParentsMemberList") },
            text = "멤버 선택",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp),
            height = 40,
            elevation = 0
        )

        // 다음 버튼
        BlueButton(
            onClick = { navController.navigate("runParentsRegister") },
            text = "다음",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun EditableAmountRow(
    initialAmount: String,
    onAmountChange: (String) -> Unit,
    isEditing: Boolean,
    onEditingChange: (Boolean) -> Unit
) {
    val textState = remember { mutableStateOf(initialAmount) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = textState.value,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() }) {
                        textState.value = newText
                        onAmountChange(newText)
                    }
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 4.dp)
            )
        } else {
            Text(
                text = "${textState.value}원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_edit),
            contentDescription = "수정",
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    onEditingChange(true)
                }
        )
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsMoneyScreenPreview() {
    RunParentsMoneyScreen(navController = rememberNavController())
}
