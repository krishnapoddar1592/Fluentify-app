package com.example.fluentifyapp.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.textFieldBorderColor
import com.example.fluentifyapp.ui.theme.textFieldTextColor
import com.example.fluentifyapp.ui.viewmodel.signup.UserDetailsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropDown() {
    val viewmodel: UserDetailsScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(viewmodel.languages[0]) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .border(
                width = 2.dp,
                color = textFieldBorderColor,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            TextField(
                value = selectedLanguage.text,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(color = textFieldTextColor, fontSize = 16.sp),
                singleLine = true,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = selectedLanguage.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(start = 8.dp, end = 4.dp)
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White).exposedDropdownSize()
            ) {

                viewmodel.languages.forEach { language ->
                    DropdownMenuItem(
                        onClick = {
                            selectedLanguage = language
                            expanded = false
                            Toast.makeText(context, language.text, Toast.LENGTH_SHORT).show()
                        },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = language.image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(end = 8.dp)
                                )
                                Text(
                                    text = language.text,
                                    fontFamily = AppFonts.rubik,
                                    fontSize = 17.sp
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}