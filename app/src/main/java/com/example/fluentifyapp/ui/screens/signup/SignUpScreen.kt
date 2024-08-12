package com.example.fluentifyapp.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.signup.SignUpScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateAfterSignUp: () -> Unit
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val signupSuccess by viewModel.signupSuccess.collectAsState()

    val quicksand = FontFamily(
        Font(resId = R.font.quicksand, weight = FontWeight.Normal),
        Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
    )
    val rubik = FontFamily(Font(resId = R.font.rubik_normal))

    LaunchedEffect(signupSuccess) {
        if (signupSuccess) {
            onNavigateAfterSignUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FBFB))
            .padding(16.dp),

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
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF208787),
                modifier = Modifier.padding(top = 84.dp)
            )

            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontFamily = rubik,
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
                        color = textFieldBorderColor,
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
                        color = textFieldBorderColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
            )

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
                onClick = { viewModel.signup() },
                modifier = Modifier
                    .width(192.dp)
                    .padding(top = 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF208787)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontFamily = rubik,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "Already Have an Account?",
                    fontSize = 12.sp
                )
                Text(
                    text = "SignIn",
                    color = Color(0xFF57AFB0),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onNavigateToSignIn() }
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