package com.ssafy.kidswallet.ui.screens.run.parents

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.ImageUtils.getBase64FromDrawable
import com.ssafy.kidswallet.ui.components.ImageUtils.getBase64FromUri
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.run.viewmodel.state.StateRunViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunParentsScreen(navController: NavController, viewModel: StateRunViewModel) {
    var text by remember { mutableStateOf("") }
    val maxChar = 15
    val showAlertDialog = remember { mutableStateOf(false) }

    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 7) }
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = calendar.timeInMillis
    }

    
    val focusRequester = remember { FocusRequester() }
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
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "목표 세우기",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = text,
                        onValueChange = { newText ->
                            if (newText.length <= maxChar) {
                                text = newText
                            }
                        },
                        placeholder = {
                            Text(
                                text = "목표를 세워 봐요",
                                style = FontSizes.h16,
                                color = Color(0x808C8595),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.Transparent)
                            .padding(horizontal = 0.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                isTextFieldFocused = focusState.isFocused
                            },
                        textStyle = FontSizes.h16.copy(fontWeight = FontWeight.Bold),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .align(Alignment.CenterVertically),
                    ) {
                        Text(
                            text = "${text.length}/$maxChar",
                            style = FontSizes.h16,
                            color = Color(0xFF8C8595),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_callendar),
                    contentDescription = "달력",
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate)),
                    style = FontSizes.h16,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "까지 같이 달리기",
                    style = FontSizes.h16,
                    color = Color(0xFF5EA0BB),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFFF7F7F7), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageUri == null) {
                Text(
                    text = "사진을 첨부할 수 있어요",
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

        Spacer(modifier = Modifier.weight(1f))

        
        BlueButton(
            onClick = {
                if (text.isBlank()) {
                    showAlertDialog.value = true 
                } else {
                    val selectedDateText = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
                    val base64String = imageUri?.let { uri ->
                        getBase64FromUri(context, uri)
                    } ?: getBase64FromDrawable(context, R.drawable.app_logo)
                    viewModel.setGoalAndDateAndBase64Text(text, selectedDateText, base64String ?: "")
                    navController.navigate("runParentsMoney")
                }
            },
            text = "다음",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }

    
    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            title = {
                Text(
                    text = "알림",
                    color = Color(0xFFFBC02D),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "목표를 입력해 주세요.",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8C8595)
                )
            },
            confirmButton = {
                BlueButton(
                    onClick = { showAlertDialog.value = false },
                    text = "확인",
                    modifier = Modifier.width(260.dp),
                    height = 40,
                    elevation = 0
                )
            }
        )
    }
}
