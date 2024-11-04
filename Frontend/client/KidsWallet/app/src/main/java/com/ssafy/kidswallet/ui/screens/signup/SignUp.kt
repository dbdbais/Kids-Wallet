package com.ssafy.kidswallet.ui.screens.signup

import com.ssafy.kidswallet.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.Top
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun SignUp(navController: NavController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordChecked by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var idFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    var passwordCheckedFocused by remember { mutableStateOf(false) }
    var nameFocused by remember { mutableStateOf(false) }
    var birthFocused by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("남") }
    var selectedRole by remember { mutableStateOf("부모") }

    var idError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordMismatchError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var birthError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Top(title = "회원가입", navController = navController)

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_full),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ){
            IdInputWithDuplicateCheck(
                id = id,
                onIdChange = { id = it },
                idError = idError,
                onIdErrorChange = { idError = it }, // 오류 상태 업데이트
                onDuplicateCheck = { /* 중복 체크 로직 구현 */ }
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = it.isEmpty() // 비밀번호가 비었는지 체크
                    passwordMismatchError = passwordChecked.isNotEmpty() && it != passwordChecked
                },
                label = { Text("비밀번호", color = if (passwordError) Color.Red else Color(0xFF8C8595)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (passwordError) Color.Red else Color(0xFF6DCEF5),
                    unfocusedBorderColor = if (passwordError) Color.Red else Color(0xFFD3D0D7)
                )
            )
            OutlinedTextField(
                value = passwordChecked,
                onValueChange = {
                    passwordChecked = it
                    passwordMismatchError = password != it
                },
                label = { Text("비밀번호 확인", color = if (passwordMismatchError) Color.Red else Color(0xFF8C8595)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (passwordMismatchError) Color.Red else Color(0xFF6DCEF5),
                    unfocusedBorderColor = if (passwordMismatchError) Color.Red else Color(0xFFD3D0D7)
                ),
                isError = passwordMismatchError // 에러 상태 반영
            )
            if (passwordMismatchError) {
                Text(
                    text = "비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = it.isEmpty() // 이름이 비었는지 체크
                },
                label = { Text("이름", color = if (nameError) Color.Red else Color(0xFF8C8595)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (nameError) Color.Red else Color(0xFF6DCEF5),
                    unfocusedBorderColor = if (nameError) Color.Red else Color(0xFFD3D0D7)
                )
            )
//            OutlinedTextField(
//                value = birth,
//                onValueChange = { birth = it },
//                label = { Text("생년월일", color = Color(0xFF8C8595)) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .onFocusChanged { focusState -> birthFocused = focusState.isFocused }
//                    .padding(bottom = 8.dp),
//                shape = RoundedCornerShape(15.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0xFF6DCEF5),
//                    unfocusedBorderColor = Color( 0xFFD3D0D7)
//                ),
//            )
            BirthdayInputField(
                birth = birth,
                onDateSelected = { selectedDate ->
                    birth = selectedDate
                    birthError = selectedDate.isEmpty()
                }
            )

            GenderSelection(selectedGender) { selectedGender = it }
            RoleSelection(selectedRole) { selectedRole = it }

            Spacer(modifier = Modifier.height(8.dp))

            GradientButton(
                onClick = {
                    // 모든 필드가 유효한지 검증
                    idError = id.isEmpty()
                    passwordError = password.isEmpty()

                    nameError = name.isEmpty()
                    birthError = birth.isEmpty()


                    // 유효한 경우 회원가입 정보 저장 로직 추가
                    if (!idError && !passwordError && !passwordMismatchError && !nameError && !birthError) {
                        Toast.makeText(
                            context,
                            "아이디: $id, 비밀번호: $password, 이름: $name, 생년월일: $birth, 성별: $selectedGender, 역할: $selectedRole",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "모든 필드를 정확히 입력해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                },
                text = "확인"
            )
        }
    }
}

@Composable
fun GradientButton(onClick: () -> Unit, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                ),
                shape = RoundedCornerShape(15.dp) // 모서리 반경 설정
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp), // 버튼 높이 조정
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun GenderSelection(selectedGender: String, onGenderSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenderButton(
            text = "남",
            isSelected = selectedGender == "남",
            onClick = { onGenderSelected("남") }
        )

        GenderButton(
            text = "여",
            isSelected = selectedGender == "여",
            onClick = { onGenderSelected("여") }
        )
    }
}

@Composable
fun GenderButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp) // 버튼 너비 설정
            .background(
                color = if (isSelected) Color(0xFF6DCEF5) else Color(0xFFD3D0D7),
                shape = RoundedCornerShape(10.dp) // 둥근 모서리
            )
            .clickable(onClick = onClick) // 클릭 이벤트 추가
            .padding(vertical = 8.dp), // 텍스트 여백 조정
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RoleSelection(selectedRole: String, onRoleSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RoleButton(
            text = "부모",
            isSelected = selectedRole == "부모",
            onClick = { onRoleSelected("부모") }
        )

        RoleButton(
            text = "아이",
            isSelected = selectedRole == "아이",
            onClick = { onRoleSelected("아이")}
        )
    }
}

@Composable
fun RoleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp) // 버튼 너비 설정
            .background(
                color = if (isSelected) Color(0xFF6DCEF5) else Color(0xFFD3D0D7),
                shape = RoundedCornerShape(10.dp) // 둥근 모서리
            )
            .clickable(onClick = onClick) // 클릭 이벤트 추가
            .padding(vertical = 8.dp), // 텍스트 여백 조정
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IdInputWithDuplicateCheck(
    id: String,
    onIdChange: (String) -> Unit,
    idError: Boolean,
    onIdErrorChange: (Boolean) -> Unit,
    onDuplicateCheck: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = id,
            onValueChange = {
                onIdChange(it)
                onIdErrorChange(it.isEmpty()) // 입력이 비어 있을 때 오류 상태로 변경
            },
            label = { Text("아이디", color = if (idError) Color.Red else Color(0xFF8C8595)) },
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (idError) Color.Red else Color(0xFF6DCEF5),
                unfocusedBorderColor = if (idError) Color.Red else Color(0xFFD3D0D7)
            )
        )

        Button(
            onClick = onDuplicateCheck,
            modifier = Modifier.height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6DCEF5)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("중복확인", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BirthdayInputField(birth: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    var birthText by remember { mutableStateOf(birth) }

    OutlinedTextField(
        value = birthText,
        onValueChange = { birthText = it }, // 직접 텍스트 입력은 막아둠
        label = { Text("생년월일", color = Color(0xFF8C8595)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                showDatePicker(context) { date ->
                    birthText = date // 선택된 날짜로 텍스트 업데이트
                    onDateSelected(date)
                }
            },
        shape = RoundedCornerShape(15.dp),
        enabled = false, // 직접 입력을 막음
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF6DCEF5),
            unfocusedBorderColor = Color(0xFFD3D0D7)
        )
    )
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // 선택된 날짜를 형식에 맞춰 전달
            val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            onDateSelected(formattedDate)
        },
        year, month, day
    ).show()
}