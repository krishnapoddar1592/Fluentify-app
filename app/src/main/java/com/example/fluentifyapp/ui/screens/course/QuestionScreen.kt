package com.example.fluentifyapp.ui.screens.course

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import com.example.fluentifyapp.ui.theme.boxBackground
import com.example.fluentifyapp.ui.theme.selectedBox
import kotlinx.coroutines.delay
import kotlin.math.floor


@Composable
fun QuestionScreen() {

}
//@Preview
@Composable
fun FillQuestionScreen() {

    var secondsRemaining by remember { mutableFloatStateOf(15f) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val progress by animateFloatAsState(
        targetValue = secondsRemaining/ 15,
        animationSpec = tween(durationMillis = 100)
    )

    LaunchedEffect(key1 = secondsRemaining, key2 = isTimerRunning) {
        if (isTimerRunning) {
            if (secondsRemaining > 0) {
                delay(100L)
                secondsRemaining-=0.1f
            } else {
                isTimerRunning = false
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp), contentAlignment = Alignment.BottomCenter)
        {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 92.dp)
                    .background(
                        color = Color(0xFF00CED1),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    ),
                contentAlignment = Alignment.TopCenter
            ){
                HeaderComponent(
                    onBackPressed = null,
                    headerText = "Essentials ${LanguageData.getLangEmoji("French")}",
                    canGoBack = true,
                    fontSize = 20.sp,
                    isColorWhite=true
                )
            }

            Box(
                modifier = Modifier.size(323.dp, 244.dp),
                contentAlignment = Alignment.TopCenter
            ){
                // Question Box
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 44.dp)
                ) {
                    // Apply the shadow using a Box with an offset
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 2.dp) // Offset shadow to the bottom
                            .shadow(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(20.dp),
                                clip = false
                            )
                    )

                    // The main content box
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Text(
                                    text = "Question ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )
                                Text(
                                    text = "7/12",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )


                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Match the following Italian travel essentials with their corresponding English translations:",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontFamily = AppFonts.quicksand,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
                // Time Circular Dial and Question Box
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(Color.White)

                ) {
                    CircularProgressIndicator(
                        progress = progress,
                        color = Color(0xFF00CED1),
                        trackColor = Color.White,
                        strokeWidth = 5.dp,
                        modifier = Modifier.size(79.dp)
                    )
                    Text(
                        text = floor(secondsRemaining).toInt().toString(),
                        color = primaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFonts.quicksand
                    )
                }


            }



        }
//        FillQuestionOptions()
        MatchQuestionOptions()



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
           NextButton()
        }

//        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun FillQuestionOptions(){
    // State to track the selected option by index (initially -1, meaning none is selected)
    var selectedIndex by remember { mutableIntStateOf(-1) }

    // List of option texts
    val options = listOf("Bon appétit", "Buon giorno", "Merci beaucoup", "Adiós")

    Column(
        modifier = Modifier
            .padding(top = 60.dp, bottom = 20.dp)
            .fillMaxWidth(),
//            .align(Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.forEachIndexed { index, text ->
            OptionBox(
                text = text,
                isSelected = selectedIndex == index, // Check if this option is selected
                onClick = {
                    selectedIndex = index // Update selected index when clicked
                }
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between options
        }
    }
}

// MatchOptionBox and handleWordSelection functions remain unchanged

@Composable
fun MatchOptionBox(
    text: String,
    isSelected: Boolean,
    number: Int,
    isDimmed: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp, 42.dp)
            .shadow(4.dp, shape = RoundedCornerShape(9.dp), clip = false)
            .background(
                color = if (isSelected) selectedBox else boxBackground,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Box for the dimming effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isDimmed) 0.5f else 1f)
        ) {
            // Text for the main content
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = AppFonts.quicksand,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Separate Box for the number, always at full opacity
        if (number > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-12).dp)
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MatchQuestionOptions() {
    val translatedWords = listOf("Valigia", "Biglietto", "Carta di credito", "Passaporto")
    val originalWords = listOf("Passport", "Hotel", "Suitcase", "Credit Card")

    var currentFirstWord by remember { mutableStateOf("") }
    var currentSecondWord by remember { mutableStateOf("") }
    var isOriginalWordSelected by remember { mutableStateOf(false) }
    var isTranslatedWordSelected by remember { mutableStateOf(false) }
    var selectedPairsCount by remember { mutableStateOf(0) }

    val wordMap = remember { mutableStateMapOf<String, String>() }
    val numberMap = remember { mutableStateMapOf<String, Int>() }
    val isLoading = remember { mutableStateOf(true) }

    // Use a MutableState for isOperationInProgress
    var isOperationInProgress by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        isLoading.value = true
        for (i in translatedWords.indices) {
            wordMap[translatedWords[i]] = ""
            wordMap[originalWords[i]] = ""
        }
        isLoading.value = false
    }

    if (isLoading.value) {
        // Show loading indicator
    } else {
        Column(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0 until 4) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MatchOptionBox(
                        text = translatedWords[i],
                        isSelected = wordMap[translatedWords[i]]!!.isNotEmpty() || currentFirstWord == translatedWords[i],
                        number = numberMap[translatedWords[i]] ?: 0,
                        isDimmed = currentFirstWord.isNotEmpty() && currentFirstWord != translatedWords[i] && isTranslatedWordSelected,
                        onClick = {
                            if (!isOperationInProgress) {
                                isOperationInProgress = true
                                handleWordSelection(
                                    selectedWord = translatedWords[i],
                                    isTranslated = true,
                                    currentFirstWord = currentFirstWord,
                                    currentSecondWord = currentSecondWord,
                                    isOriginalWordSelected = isOriginalWordSelected,
                                    isTranslatedWordSelected = isTranslatedWordSelected,
                                    wordMap = wordMap,
                                    numberMap = numberMap,
                                    onUpdateCurrentWords = { first, second ->
                                        currentFirstWord = first
                                        currentSecondWord = second
                                    },
                                    onUpdateSelectionFlags = { original, translated ->
                                        isOriginalWordSelected = original
                                        isTranslatedWordSelected = translated
                                    },
                                    onUpdateSelectedPairsCount = { selectedPairsCount = it }
                                )
                                isOperationInProgress = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    MatchOptionBox(
                        text = originalWords[i],
                        isSelected = wordMap[originalWords[i]]!!.isNotEmpty() || currentFirstWord == originalWords[i],
                        number = numberMap[originalWords[i]] ?: 0,
                        isDimmed = currentFirstWord.isNotEmpty() && currentFirstWord != originalWords[i] && isOriginalWordSelected,
                        onClick = {
                            if (!isOperationInProgress) {
                                isOperationInProgress = true
                                handleWordSelection(
                                    selectedWord = originalWords[i],
                                    isTranslated = false,
                                    currentFirstWord = currentFirstWord,
                                    currentSecondWord = currentSecondWord,
                                    isOriginalWordSelected = isOriginalWordSelected,
                                    isTranslatedWordSelected = isTranslatedWordSelected,
                                    wordMap = wordMap,
                                    numberMap = numberMap,
                                    onUpdateCurrentWords = { first, second ->
                                        currentFirstWord = first
                                        currentSecondWord = second
                                    },
                                    onUpdateSelectionFlags = { original, translated ->
                                        isOriginalWordSelected = original
                                        isTranslatedWordSelected = translated
                                    },
                                    onUpdateSelectedPairsCount = { selectedPairsCount = it }
                                )
                                isOperationInProgress = false
                            }
                        }
                    )
                }
            }
        }
    }
}

// The MatchOptionBox composable remains unchanged

fun handleWordSelection(
    selectedWord: String,
    isTranslated: Boolean,
    currentFirstWord: String,
    currentSecondWord: String,
    isOriginalWordSelected: Boolean,
    isTranslatedWordSelected: Boolean,
    wordMap: MutableMap<String, String>,
    numberMap: MutableMap<String, Int>,
    onUpdateCurrentWords: (String, String) -> Unit,
    onUpdateSelectionFlags: (Boolean, Boolean) -> Unit,
    onUpdateSelectedPairsCount: (Int) -> Unit
) {
    when {
        // Case 1: Selecting the first word of a pair
        currentFirstWord.isEmpty() && (wordMap[selectedWord]?.isEmpty() == true) -> {
            onUpdateCurrentWords(selectedWord, "")
            onUpdateSelectionFlags(!isTranslated, isTranslated)
        }
        // Case 2: Selecting the second word of a pair
        currentSecondWord.isEmpty() && ((isTranslated && isOriginalWordSelected) || (!isTranslated && isTranslatedWordSelected)) -> {


            if(wordMap[selectedWord]!=""){
                val currentPair=wordMap[selectedWord]
                wordMap[currentPair!!]=""
                val removedNumber = numberMap[selectedWord]
                numberMap.remove(selectedWord)
                numberMap.remove(currentPair)
                // Adjust numbers of pairs formed after the removed pair
                if (removedNumber != null) {
                    numberMap.forEach { (word, number) ->
                        if (number > removedNumber) {
                            numberMap[word] = number - 1
                        }
                    }
                }

            }
            val newPairNumber = numberMap.values.maxOrNull()?.plus(1) ?: 1
            wordMap[currentFirstWord] = selectedWord
            wordMap[selectedWord] = currentFirstWord
            numberMap[currentFirstWord] = newPairNumber
            numberMap[selectedWord] = newPairNumber
            onUpdateCurrentWords("", "")
            onUpdateSelectionFlags(false, false)
            onUpdateSelectedPairsCount(numberMap.size / 2)
        }
        // Case 3: Deselecting a word (breaking a pair)
        selectedWord == currentFirstWord || (wordMap[selectedWord]?.isNotEmpty() == true && currentFirstWord.isEmpty()) -> {
            val pairedWord = wordMap[selectedWord] ?: ""
            wordMap[selectedWord] = ""
            wordMap[pairedWord] = ""
            val removedNumber = numberMap[selectedWord]
            numberMap.remove(selectedWord)
            numberMap.remove(pairedWord)
            onUpdateCurrentWords("", "")
            onUpdateSelectionFlags(false, false)
            onUpdateSelectedPairsCount(numberMap.size / 2)

            // Adjust numbers of pairs formed after the removed pair
            if (removedNumber != null) {
                numberMap.forEach { (word, number) ->
                    if (number > removedNumber) {
                        numberMap[word] = number - 1
                    }
                }
            }
        }
    }
}







@Composable
fun OptionBox(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(273.dp, 42.dp)
            .shadow(4.dp, shape = RoundedCornerShape(9.dp), clip = false)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(
                onClick = onClick,
                // Disable default click indication (highlight)
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text label of the option
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = AppFonts.quicksand
            )
            Spacer(modifier = Modifier.weight(1f))

            // The clickable circle on the right
            ClickableCircle(isFilled = isSelected)
        }
    }
}

// ClickableCircle composable to display the circle
@Composable
fun ClickableCircle(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .size(15.dp)
            .border(
                width = 2.dp, // Border width
                color = if (isFilled) Color(0xFF00CED1) else Color.Gray, // Border color changes based on selection
                shape = CircleShape
            )
            .background(
                color = if (isFilled) Color(0xFF00CED1) else Color.Transparent, // Fill color based on selection
                shape = CircleShape
            )
    )
}
@Preview
@Composable
fun NextButton(){
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF00CED1))
            .clickable(onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.next_arrow),
            contentDescription = "Back Button",
            Modifier.size(35.dp)
        )
    }

}



