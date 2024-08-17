package com.example.fluentifyapp.ui.viewmodel.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownMenu(viewModel: UserDetailsScreenViewModel = hiltViewModel()) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isDropdownExpanded by viewModel.isLanguageDropdownExpanded.collectAsState()
    val languages = viewModel.languages

    ExposedDropdownMenuBox(
        expanded = isDropdownExpanded,
        onExpandedChange = { viewModel.setLanguageDropdownExpanded(!isDropdownExpanded) }
    ) {
        OutlinedTextField(
            value = selectedLanguage.text,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Language") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isDropdownExpanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(56.dp)
        )

        ExposedDropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { viewModel.setLanguageDropdownExpanded(false) },
            modifier = Modifier
                .height(300.dp) // Adjust the height as needed
                .width(250.dp) // Adjust the width as needed
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // Add vertical scrolling
            ) {
                languages.forEach { language ->
                    DropdownMenuItem(
                        text = {
                            Row(modifier = Modifier.padding(4.dp)) {
                                Image(
                                    painter = painterResource(id = language.image),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(language.text)
                            }
                        },
                        onClick = {
                            viewModel.setSelectedLanguage(language)
                            viewModel.setLanguageDropdownExpanded(false)
                        }
                    )
                }
            }
        }
    }
}
