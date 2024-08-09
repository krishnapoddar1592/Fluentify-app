package com.example.fluentifyapp.ui.screens.login
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModelFactory

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    onNavigateAfterLogin: () -> Unit
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val quicksand = FontFamily(
        Font(resId = R.font.quicksand, weight = FontWeight.Normal),
        Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
    )
    val varela=FontFamily(
        Font(resId = R.font.varela_round)
    )

    val rubik=FontFamily(
        Font(resId=R.font.rubik_normal)
    )


    val loginSuccess by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onNavigateAfterLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            onValueChange = { viewModel.setUsername(it) },
            label = { Text("Email Or Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.setPassword(it) },
            label = { Text("Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible) R.drawable.hidepass else R.drawable.showpass),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
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
            onClick = { viewModel.login() },
            modifier = Modifier
                .width(192.dp)
                .height(43.dp)
                .padding(top = 28.dp)
        ) {
            Text("Login")
        }

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
            modifier = Modifier.padding(top = 28.dp)
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
                    .clickable { onNavigateToSignUp()  }
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//        val navController = rememberNavController()
//        val viewModel = remember { LoginScreenViewModel() }
//        LoginScreen(navController = navController, viewModel = viewModel)
//
//}