package com.ssafy.kidswallet.ui.screens.run.parents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun RunParentsMemberListScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    viewModel: RunMemberViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isTextFieldFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null 
            ) {
                
                keyboardController?.hide()
                isTextFieldFocused = false
            }
    ) {
        
        Top(
            title = "멤버 목록",
            navController = navController
        )

        Spacer(modifier = Modifier.height(16.dp))

        
        SearchTextField(
            placeholderText = "이름 또는 번호로 검색 할 수 있어요",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newText ->
                searchText = newText
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        
        val filteredMembers = storedUserData?.relations?.filter {
            (it.userName?.contains(searchText, ignoreCase = true) ?: false)
        } ?: emptyList()

        
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
                        .clickable {
                            member.userName?.let {
                                
                                viewModel.toggleMemberSelection(
                                    member = it,
                                    realName = member.userRealName ?: "N/A",
                                    userId = member.userId
                                )
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    
                    Image(
                        painter = painterResource(id = R.drawable.character_run_member),
                        contentDescription = "멤버",
                        modifier = Modifier
                            .size(55.dp)
                            .padding(end = 8.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = member.userRealName ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = member.userName ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    
                    Image(
                        painter = painterResource(
                            id = if (viewModel.selectedMember == member.userName) {
                                R.drawable.icon_check_active 
                            } else {
                                R.drawable.icon_check 
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
    val maxChar = 15

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            
            if (newText.length <= maxChar) {
                text = newText
                onValueChange(newText)
            }
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
            .background(Color(0xFFF7F7F7), shape = CircleShape),
        
        singleLine = true, 
        visualTransformation = VisualTransformation { textInput ->
            
            TransformedText(
                text = AnnotatedString(textInput.text.replace("\t", "")),
                offsetMapping = OffsetMapping.Identity
            )
        }
    )
}

