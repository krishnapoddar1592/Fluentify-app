package com.example.fluentifyapp.ui.screens.signup

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import com.example.fluentifyapp.ui.viewmodel.signup.UserDetailsScreenViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsScreenViewModel = hiltViewModel(),
    onNavigateAfterSignIn: () -> Unit,
    onBackPressed: () -> Unit
) {

    val name by viewModel.name.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLanguageDropdownExpanded by viewModel.isLanguageDropdownExpanded.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    val dobError by viewModel.dobError.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var textFieldPosition by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

    val quicksand = FontFamily(
        Font(resId = R.font.quicksand, weight = FontWeight.Normal),
        Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
    )

    val rubik = FontFamily(Font(resId = R.font.rubik_normal))

    val scope = rememberCoroutineScope()

    // State to manage the visibility of the DatePickerDialog
    val showDatePicker = remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Trigger DatePickerDialog if showDatePicker is true
    LaunchedEffect(showDatePicker.value) {
        if (showDatePicker.value) {
            Log.d("UserDetailsScreen", "Showing DatePickerDialog")
        }
    }

    // In the DatePickerDialog
    if (showDatePicker.value) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        Log.d("UserDetailsScreen", "Preparing to show DatePickerDialog")

        android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                Log.d("UserDetailsScreen", "Date selected: $formattedDate")
                viewModel.setDob(formattedDate)
                showDatePicker.value = false // Dismiss the dialog after selection
            },
            year, month, day
        ).show()
        showDatePicker.value=false

        Log.d("UserDetailsScreen", "DatePickerDialog shown")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FBFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with Back Button and Sign Up Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.backarrowgreen),
                contentDescription = "Back Button",
                modifier = Modifier
                    .size(width = 24.dp, height = 23.dp)
                    .clickable { onBackPressed() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontFamily = rubik,
                color = Color(0xFF7B7B7B),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        // Profile completion title and description
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

        // Name input field
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.setName(it) },
            placeholder = {
                Text("Enter Your Name", style = TextStyle(fontSize = 16.sp, color = textFieldBorderColor))
            },
//            isError = nameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .border(
                    width = 2.dp,
                    color = if (nameError != null) Color(0xFFD32F2F) else textFieldBorderColor,
                    shape = RoundedCornerShape(20.dp)
                ),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
            singleLine = true,
        )
        if (nameError != null) {
            Text(
                text = nameError!!,
                color = Color(0xFFD32F2F),
                modifier = Modifier.align(Alignment.Start),
                fontFamily = rubik,
                fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(
                    width = 2.dp,
                    color = if (dobError != null) Color(0xFFD32F2F) else textFieldBorderColor,
                    shape = RoundedCornerShape(20.dp)
                )

        ) {
            Text(
                text = if (dob.isEmpty()) "Select Date of Birth" else dob,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Log.d("UserDetailsScreen", "Date of Birth field clicked")
                        showDatePicker.value = true
                    }
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(16.dp),
                color = if (dob.isEmpty()) textFieldBorderColor else textFieldTextColor,

            )
        }
        if (dobError != null) {
            Text(
                text = dobError!!,
                color = Color(0xFFD32F2F),
                modifier = Modifier.align(Alignment.Start),
                fontFamily = rubik,
                fontSize = 14.sp
            )
        }
        LanguageDropDown()


        // Continue button
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



        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

