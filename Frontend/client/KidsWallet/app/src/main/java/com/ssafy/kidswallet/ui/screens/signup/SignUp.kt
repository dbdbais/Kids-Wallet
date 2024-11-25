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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.viewmodel.SignUpViewModel
import java.util.Calendar
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavController, viewModel: SignUpViewModel = viewModel()) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordChecked by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }

    var selectedGender by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }

    val errorState = viewModel.errorState.collectAsState().value
    val navigateToLogin by viewModel.navigateToLogin.collectAsState()

    var idError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordMismatchError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var birthError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var isSubmitting by remember { mutableStateOf(false) }

    if (navigateToLogin) {
        navController.navigate("loginRouting")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .clickable(
                indication = null, 
                interactionSource = remember { MutableInteractionSource() } 
            ) {
                
                focusManager.clearFocus()
            },
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
            OutlinedTextField(
                value = id,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isLetterOrDigit() } 
                    if (filteredInput.length <= 15) { 
                        id = filteredInput
                        idError = filteredInput.isEmpty() 
                    }
                },
                label = { Text("아이디", color = if (idError) Color.Red else Color(0xFF8C8595)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (idError) Color.Red else Color(0xFF6DCEF5),
                    unfocusedBorderColor = if (idError) Color.Red else Color(0xFFD3D0D7)
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 40) {
                        password = it
                        passwordError = it.isEmpty() 
                        passwordMismatchError = passwordChecked.isNotEmpty() && it != passwordChecked
                    }
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
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = passwordChecked,
                onValueChange = {
                    if (it.length <= 40) {
                        passwordChecked = it
                        passwordMismatchError = password != it
                    }
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
                singleLine = true,
                isError = passwordMismatchError 
            )
            if (passwordMismatchError) {
                Text(
                    text = "비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    style = FontSizes.h12,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isLetterOrDigit() } 
                    if (filteredInput.length <= 15) {
                        name = filteredInput
                        nameError = filteredInput.isEmpty() 
                    }
                },
                label = { Text("이름", color = if (nameError) Color.Red else Color(0xFF8C8595)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (nameError) Color.Red else Color(0xFF6DCEF5),
                    unfocusedBorderColor = if (nameError) Color.Red else Color(0xFFD3D0D7)
                ),
                singleLine = true
            )
            BirthdayInputField(
                birth = birth,
                onDateSelected = { selectedDate ->
                    birth = selectedDate
                    birthError = selectedDate.isEmpty()
                }
            )

            GenderSelection(selectedGender) { selectedGender = it }
            RoleSelection(selectedRole) { selectedRole = it }

            Spacer(modifier = Modifier.height(15.dp))

            BlueButton(
                onClick = {
                    
                    idError = id.isEmpty()
                    passwordError = password.isEmpty()

                    nameError = name.isEmpty()
                    birthError = birth.isEmpty()


                    
                    if (!idError && !passwordError && !passwordMismatchError && !nameError && !birthError && (password == passwordChecked) && password.length >= 8) {
                        if (!isSubmitting) {
                            isSubmitting = true
                            viewModel.registerUser(
                                userName = id,
                                userPassword = password,
                                userBirth = birth,
                                userGender = selectedGender,
                                userRealName = name,
                                userRole = selectedRole
                            )

                            
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000) 
                                isSubmitting = false
                            }
                        }
                    } else if (password.length < 8) {
                        Toast.makeText(context, "비밀번호는 8자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "모든 필드를 정확히 입력해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.width(400.dp),
                height = 40,
                text = "확인"
            )
        }
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
            isSelected = selectedGender == "MALE",
            onClick = { onGenderSelected("MALE") }
        )

        GenderButton(
            text = "여",
            isSelected = selectedGender == "FEMALE",
            onClick = { onGenderSelected("FEMALE") }
        )
    }
}

@Composable
fun GenderButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp) 
            .background(
                color = if (isSelected) Color(0xFF6DCEF5) else Color(0xFFD3D0D7),
                shape = RoundedCornerShape(10.dp) 
            )
            .clickable(onClick = onClick) 
            .padding(vertical = 8.dp), 
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
    val showParentHelp = remember { mutableStateOf(false) }
    val showKidHelp = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
        ) {
            RoleButton(
                text = "부모",
                isSelected = selectedRole == "PARENT",
                onClick = { onRoleSelected("PARENT") }
            )
            Image(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "부모 설명 아이콘",
                modifier = Modifier
                    
                    .clickable { showParentHelp.value = true } 
                    .size(24.dp) 
            )
        }
        Column (
            modifier = Modifier
        ){
            RoleButton(
                text = "아이",
                isSelected = selectedRole == "CHILD",
                onClick = { onRoleSelected("CHILD")}
            )
            Image(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "아이 설명 아이콘",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { showKidHelp.value = true } 
                    .size(24.dp) 
            )
        }
    }
    
    if (showParentHelp.value) {
        PopupHelp(
            text1 = "아이에게 미션을 주고 용돈을 줄 수 있어요!",
            text2 = "더 많은 기능을 사용해 보세요!",
            onDismiss = { showParentHelp.value = false }
        )
    }

    if (showKidHelp.value) {
        PopupHelp(
            text1 = "미션을 수행하고 용돈을 받을 수 있어요!",
            text2 = "미션을 하고 부자가 되어봐요!",
            onDismiss = { showKidHelp.value = false }
        )
    }
}

@Composable
fun RoleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp) 
            .background(
                color = if (isSelected) Color(0xFF6DCEF5) else Color(0xFFD3D0D7),
                shape = RoundedCornerShape(10.dp) 
            )
            .clickable(onClick = onClick) 
            .padding(vertical = 8.dp), 
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
fun BirthdayInputField(birth: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    var birthText by remember { mutableStateOf(birth) }

    OutlinedTextField(
        value = birthText,
        onValueChange = { birthText = it }, 
        label = { Text("생년월일", color = Color(0xFF8C8595)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                showDatePicker(context) { date ->
                    birthText = date 
                    onDateSelected(date)
                }
            },
        shape = RoundedCornerShape(15.dp),
        enabled = false, 
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
            
            val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            onDateSelected(formattedDate)
        },
        year, month, day
    ).show()
}

@Composable
fun PopupHelp(text1: String, text2: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), 
        contentAlignment = Alignment.Center 
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { onDismiss() },
            properties = PopupProperties(focusable = true)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6DCEF5),
                                Color(0xFF99DDF8)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        BorderStroke(2.dp, Color(0xFF6DCEF5)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = text1,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text2,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}