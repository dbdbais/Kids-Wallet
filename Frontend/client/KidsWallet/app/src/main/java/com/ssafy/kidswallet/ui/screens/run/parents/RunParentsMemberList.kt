package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.Top
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import com.ssafy.kidswallet.viewmodel.RunMemberViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*

@Composable
fun RunParentsMemberListScreen(navController: NavController, viewModel: RunMemberViewModel = viewModel()) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 상단 제목 및 닫기 버튼
        Top(
            title = "멤버 목록",
            navController = navController
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 검색창
        SearchTextField(
            placeholderText = "이름 또는 번호로 검색 할 수 있어요",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newText ->
                searchText = newText
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 검색 텍스트에 따라 멤버 필터링
        val filteredMembers = viewModel.members.filter {
            it.first.contains(searchText, ignoreCase = true) || it.second.contains(searchText, ignoreCase = true)
        }

        // LazyColumn을 사용하여 필터링된 멤버 리스트 표시
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            items(filteredMembers) { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { viewModel.toggleMemberSelection(member.first) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 캐릭터 이미지 (그대로 유지)
                    Image(
                        painter = painterResource(id = R.drawable.character_run_member),
                        contentDescription = member.first,
                        modifier = Modifier
                            .size(55.dp)
                            .padding(end = 8.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = member.first,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = member.second,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    // 체크 아이콘 (상태에 따라 다르게 표시)
                    Image(
                        painter = painterResource(
                            id = if (viewModel.selectedMember == member.first) {
                                R.drawable.icon_check_active // 선택된 상태
                            } else {
                                R.drawable.icon_check // 선택되지 않은 상태
                            }
                        ),
                        contentDescription = "선택 상태",
                        modifier = Modifier.size(24.dp)
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        BlueButton(
            onClick = {
                navController.popBackStack(route = "runParentsMoney", inclusive = true)
                navController.navigate("runParentsMoney")
            },
            text = "선택 완료",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    placeholderText: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onValueChange(newText)
        },
        placeholder = {
            // 텍스트만 플레이스홀더에 포함
            Text(text = placeholderText, color = Color(0x808C8595))
        },
        leadingIcon = {
            // 항상 표시되는 검색 이미지
            Image(
                painter = painterResource(id = R.drawable.icon_search2),
                contentDescription = "검색",
                modifier = Modifier.size(20.dp)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), shape = CircleShape)
    )
}



@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560"
)
@Composable
fun RunParentsMemberListScreenPreview() {
    RunParentsMemberListScreen(navController = rememberNavController())
}