package com.example.fluentifyapp.ui.screens.signup

import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import com.example.fluentifyapp.ui.viewmodel.signup.UserDetailsScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsScreenViewModel = hiltViewModel(),
    onNavigateAfterSignIn: () -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val name by viewModel.name.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val quicksand = FontFamily(
        Font(resId = R.font.quicksand, weight = FontWeight.Normal),
        Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FBFB))
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
            text = "Complete your details",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.setName(it) },
            placeholder = { Text("Name", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
            singleLine = true
        )

        OutlinedTextField(
            value = dob,
            onValueChange = { /* Date input via picker handled separately */ },
            placeholder = { Text("Date of Birth", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clickable {
                    viewModel.showDatePicker(context)
                }
                .background(Color.White, shape = RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Language Dropdown (Simplified for Compose)
        DropdownMenu(
            expanded = viewModel.isLanguageDropdownExpanded,
            onDismissRequest = { viewModel.setLanguageDropdownExpanded(false) }
        ) {
            viewModel.languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(text = language) },
                    onClick = {
                        viewModel.setSelectedLanguage(language)
                        viewModel.setLanguageDropdownExpanded(false)
                    }
                )
            }
        }

        Button(
            onClick = {
                scope.launch {
                    viewModel.saveUserData()
                    onNavigateAfterSignIn()
                }
            },
            modifier = Modifier
                .width(192.dp)
                .padding(top = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF208787)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Continue",
                fontFamily = quicksand,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Back",
            color = Color(0xFF57AFB0),
            fontSize = 12.sp,
            modifier = Modifier.clickable { onBackPressed() }
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
