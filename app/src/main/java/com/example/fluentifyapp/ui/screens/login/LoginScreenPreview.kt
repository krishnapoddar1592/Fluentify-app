package com.example.fluentifyapp.ui.screens.login

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO // Force light theme
)
@Composable
fun PreviewLoginScreen() {
    LoginScreenPreview()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenPreview() {
    // Mock values for preview purposes
    var username by remember { mutableStateOf("User123") }
    var password by remember { mutableStateOf("password") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val quicksand = if (LocalInspectionMode.current) {
        FontFamily.SansSerif
    } else {
        FontFamily(
            Font(resId = R.font.quicksand, weight = FontWeight.Normal),
            Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
            Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
        )
    }

    val rubik = if (LocalInspectionMode.current) {
        FontFamily.SansSerif
    } else {
        FontFamily(Font(resId = R.font.rubik_normal))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Login",
                fontSize = 18.sp,
                fontFamily = rubik,
                modifier = Modifier.padding(top = 24.dp)
            )


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = {
                    Text(
                        "Email Or Username",
                        style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)
                    )
                },
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
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        "Password",
                        style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)
                    )
                },

                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible },
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
                    .clickable { /* TODO: Forgot password logic */ }
            )



            Button(
                onClick = { /* TODO: Login logic */ },
                modifier = Modifier
                    .width(192.dp)
                    .padding(top = 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF208787)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Login",
                    fontFamily = FontFamily(Font(R.font.rubik)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // This will push the content below to the bottom
//        Spacer(modifier = Modifier.weight(0.1f))

        // Bottom content
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
                    .clickable { /* TODO: Google Sign-In logic */ }
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
                        .clickable { /* TODO: Navigate to SignUp */ }
                )
            }
        }
    }
}