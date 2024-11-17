package com.ssafy.kidswallet.ui.screens.run.parents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.ssafy.kidswallet.viewmodel.RunMemberViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults

@Composable
fun RunParentsMemberListScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    viewModel: RunMemberViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
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

        // storedUserData.relations 기반으로 필터링
        val filteredMembers = storedUserData?.relations?.filter {
            (it.userName?.contains(searchText, ignoreCase = true) ?: false)
        } ?: emptyList()

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
                        .clickable { member.userName?.let { viewModel.toggleMemberSelection(it) } },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 캐릭터 이미지
                    Image(
                        painter = painterResource(id = R.drawable.character_run_member),
                        contentDescription = "멤버",
                        modifier = Modifier
                            .size(55.dp)
                            .padding(end = 8.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = member.userName ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = member.userGender ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    // 체크 아이콘 (상태에 따라 다르게 표시)
                    Image(
                        painter = painterResource(
                            id = if (viewModel.selectedMember == member.userName) {
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
                val selectedUserName = viewModel.selectedMember ?: "응애재훈"
//                navController.popBackStack(route = "runParentsMoney", inclusive = true)
//                Log.d("NavigationDebug", "Navigating to runParentsMoney/$selectedUserName")
//                navController.navigate("runParentsMoney/$selectedUserName")
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
            Text(text = placeholderText, color = Color(0x808C8595))
        },
        leadingIcon = {
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
