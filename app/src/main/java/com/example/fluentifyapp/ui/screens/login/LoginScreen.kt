package com.example.fluentifyapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    onNavigateAfterLogin: () -> Unit
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val usernameError by viewModel.userNameError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val isUserNameFilled by viewModel.isUsernameFilled.collectAsState()
    val isPasswordFilled by viewModel.isPasswordFilled.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onNavigateAfterLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FBFB))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = "Fluentify",
                fontSize = 48.sp,
                fontFamily = AppFonts.quicksand,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF208787),
                modifier = Modifier.padding(top = 84.dp)
            )

            Text(
                text = "Login",
                fontSize = 18.sp,
                fontFamily = AppFonts.rubik,
                modifier = Modifier.padding(top = 24.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.setUsername(it) },
                placeholder = { Text("Email Or Username", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .border(
                        width = 2.dp,
                        if (usernameError != null) Color(0xFFD32F2F) else textFieldBorderColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
                singleLine = true,
            )
            if (usernameError != null) {
                Text(
                    text = usernameError!!,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.align(Alignment.Start),
                    fontFamily = AppFonts.rubik,
                    fontSize = 14.sp
                )
            }

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.setPassword(it) },
                placeholder = { Text("Password", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.togglePasswordVisibility() },
                        modifier = Modifier.padding(end = 3.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = if (isPasswordVisible) R.drawable.hidepass else R.drawable.showpass),
                            contentDescription = "Toggle password visibility",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .width(21.dp)
                                .height(20.dp)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .border(
                        width = 2.dp,
                        color = if (passwordError != null) Color(0xFFD32F2F) else textFieldBorderColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
            )
            if (passwordError != null) {
                Text(
                    text = passwordError!!,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.align(Alignment.Start),
                    fontFamily = AppFonts.rubik,
                    fontSize = 14.sp
                )
            }

            Text(
                text = "Forgot your password?",
                color = Color(0xFF57AFB0),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 28.dp)
                    .clickable { viewModel.forgotPassword() }
            )

            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .width(192.dp)
                    .padding(top = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF208787)),
                shape = RoundedCornerShape(14.dp),
                enabled= usernameError==null && passwordError==null && isUserNameFilled && isPasswordFilled
            ) {
                Text(
                    text = "Login",
                    fontFamily = AppFonts.rubik,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Or",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.signin),
                contentDescription = "Sign in with Google",
                modifier = Modifier
                    .width(179.dp)
                    .height(37.dp)
                    .padding(top = 16.dp)
                    .clickable { viewModel.googleSignIn() }
            )

            Row(
                modifier = Modifier.padding(top = 28.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Don't Have an Account?",
                    fontSize = 12.sp
                )
                Text(
                    text = "SignUp",
                    color = Color(0xFF57AFB0),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onNavigateToSignUp() }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

