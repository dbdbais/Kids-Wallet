package com.ssafy.kidswallet.ui.screens.run.parents

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.ssafy.kidswallet.ui.components.Top
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale // `java.util.Locale`만 남김

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunParentsScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    val maxChar = 30

    // 사진 선택
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    // 날짜 상태를 관리하는 변수
    val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 7) } // 7일 후 날짜로 초기화
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }
    val context = LocalContext.current

    // DatePickerDialog 설정
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "목표 세우기",
            style = FontSizes.h24,
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
                    shape = RoundedCornerShape(15.dp)
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
                                text = "목표를 세워봐요",
                                style = FontSizes.h16,
                                color = Color(0xFF8C8595),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.Transparent)
                            .padding(horizontal = 0.dp),
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
                    text = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date(selectedDate)),
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
            Text(
                text = "사진을 첨부할 수 있어요",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (imageUri == null) {
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
            onClick = { navController.navigate("runParentsMoney") },
            text = "다음",
            modifier = Modifier.width(400.dp).padding(bottom = 20.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsScreenPreview() {
    RunParentsScreen(navController = rememberNavController())
}
