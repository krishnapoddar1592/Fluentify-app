package com.example.fluentifyapp.ui.screens.signup

import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreenPreview(
    onNavigateAfterSignIn: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    // Mock data
    var name by remember { mutableStateOf("John Doe") }
    var dob by remember { mutableStateOf("1990-01-01") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var isLoading by remember { mutableStateOf(false) }
    var isLanguageDropdownExpanded by remember { mutableStateOf(false) }

    val languages = listOf("English", "Spanish", "French", "German", "Mandarin")

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

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FBFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Stretch the Row to fill the screen width
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically // Align vertically center
        ) {
            // Back Button Image on the left
            Image(
                painter = painterResource(id = R.drawable.backarrowgreen),
                contentDescription = "Back Button",
                modifier = Modifier
                    .size(width = 24.dp, height = 23.dp)
                    .clickable {
                        // Handle back button click here
                    }
            )

            // Spacer to push the Text to the center
            Spacer(modifier = Modifier.weight(1f))

            // Sign Up Text in the center
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontFamily = rubik,
                color = Color(0xFF7B7B7B),
                modifier = Modifier.weight(1f), // Distribute space equally
                textAlign = TextAlign.Center // Center align the text within its space
            )

            // Spacer on the right to balance the Row layout
            Spacer(modifier = Modifier.weight(1f))
        }


        Text(
            text = "Complete Profile",
            fontSize = 34.sp,
            fontFamily = quicksand,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF208787),
            modifier = Modifier.padding(top = 50.dp)
        )

        Text(
            text = "Complete your details or continue with social media",
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 24.dp),
            color = Color(0xFF7B7B7B)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = {
                Text(
                    "Enter Your Name",
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
            value = dob,
            onValueChange = { dob = it },
            placeholder = { Text("Date of Birth", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clickable {
                    // Simulate a date picker being shown
                    Toast
                        .makeText(context, "Date picker clicked", Toast.LENGTH_SHORT)
                        .show()
                }
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(
                    width = 2.dp,
                    color = textFieldBorderColor,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                text = selectedLanguage,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isLanguageDropdownExpanded = true }
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(16.dp),

                color = Color.Black
            )

            DropdownMenu(
                expanded = isLanguageDropdownExpanded,
                onDismissRequest = { isLanguageDropdownExpanded = false }
            ) {
                languages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language) },
                        onClick = {
                            selectedLanguage = language
                            isLanguageDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    // Simulate saving user data
                    Toast.makeText(context, "User data saved", Toast.LENGTH_SHORT).show()
                    isLoading = false
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



        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserDetailsScreen() {
    UserDetailsScreenPreview()
}
