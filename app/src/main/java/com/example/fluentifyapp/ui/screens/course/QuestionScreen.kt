package com.example.fluentifyapp.ui.screens.course

import android.graphics.BlurMaskFilter
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import com.example.fluentifyapp.data.model.FillQuestion
import com.example.fluentifyapp.data.model.LessonScoreData
import com.example.fluentifyapp.data.model.MatchQuestion
import com.example.fluentifyapp.ui.screens.home.shimmerLoadingAnimation
import com.example.fluentifyapp.ui.theme.boxBackground
import com.example.fluentifyapp.ui.viewmodel.course.QuestionScreenViewModel
import kotlinx.coroutines.delay
import java.util.LinkedList
import java.util.Queue
import kotlin.math.floor


@Composable
fun QuestionScreen(
    viewModel: QuestionScreenViewModel,
    onBackPressed: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    canGoBack: Boolean = false,
    userId: String = "",
    courseId: Int = -1,
    lessonId: Int = -1,
    questionOffset: Int = 0,
    lessonName: String = "",
    lessonLang: String = "",
    totalQuestions: Int = 0,
    onNavigateToLessonScoreScreen: (LessonScoreData) -> Unit,

    ) {

    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.init(courseId,lessonId,userId,questionOffset)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
        ) {
            item{
                if (isLoading) {
                    ShimmerQuestionScreen()
                } else {
                    ActualQuestionScreen(viewModel,onBackPressed,canGoBack,onNavigateToHomeScreen,lessonName,lessonLang,totalQuestions,questionOffset,onNavigateToLessonScoreScreen)
                }
            }
        }
    }

}

@Composable
fun ShimmerQuestionScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .shimmerLoadingAnimation())

}

//@Preview
@Composable
fun ActualQuestionScreen(
    viewModel: QuestionScreenViewModel,
    onBackPressed: () -> Unit,
    canGoBack: Boolean,
    onNavigateToHomeScreen: () -> Unit,
    lessonName: String,
    lessonLang: String,
    totalQuestions: Any,
    questionOffset: Int,
    onNavigateToLessonScoreScreen: (LessonScoreData) -> Unit,
) {

    val secondsRemaining =viewModel.secondsRemaining.collectAsState()

    val isTimerRunning = viewModel.isTimerRunning.collectAsState()

    val questionBatch=viewModel.questionBatch.collectAsState()
    val currentQuestionIndex=viewModel.currentQuestionIndex.collectAsState()

    val question=questionBatch.value[currentQuestionIndex.value]

    val currentQuesNo=viewModel.currentQuestionNo.collectAsState()

    val allQuestionsCompleted=viewModel.allQuestionsCompleted.collectAsState()

    val questionsAnswered=viewModel.questionsAnsweredCount.collectAsState()

    val correctAnswers=viewModel.correctAnswers.collectAsState()

    val totalTimeTaken=viewModel.totalTimeTaken.collectAsState()

    val progress by animateFloatAsState(
        targetValue = secondsRemaining.value/ 15,
        animationSpec = tween(durationMillis = 100)
    )

    LaunchedEffect(key1 = secondsRemaining.value, key2 = isTimerRunning.value,key3 = currentQuestionIndex.value) {
        if (isTimerRunning.value) {
            if (secondsRemaining.value > 0) {
                delay(100L)
                var updatedTime=if(secondsRemaining.value>=0.1) secondsRemaining.value-0.1f else 0f
//                Log.e("secondsRemaining",secondsRemaining.value.toString() )
//                if(secondsRemaining-0.1f)
                viewModel.updateSecondsRemaining(updatedTime)
            } else {
                viewModel.setIsTimerRunning(false)

                viewModel.onQuestionAnswered()
            }
        }
    }
    LaunchedEffect(key1=allQuestionsCompleted.value) {
        if(allQuestionsCompleted.value){
            onNavigateToLessonScoreScreen(
                LessonScoreData(
                    totalQuestions = questionBatch.value.size,
                    questionsAnswered = questionsAnswered.value,
                    correctAnswers = correctAnswers.value,
                    incorrectAnswers = questionsAnswered.value-correctAnswers.value,
                    averageTime = totalTimeTaken.value/questionBatch.value.size,
                    totalTime = totalTimeTaken.value,
                    xpEarned = correctAnswers.value*15,
                    totalScore = correctAnswers.value.toFloat()/questionBatch.value.size.toFloat()*100f
                )

            )
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
                    onBackPressed = onBackPressed,
                    headerText = "$lessonName ${LanguageData.getLangEmoji(lessonLang)}",
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
                                    text = "${currentQuesNo.value}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )
                                Text(
                                    text = "/$totalQuestions",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )


                            }
                            var questionText=question.questionText
                            if(question.questionType=="Fill"){
                                val fillQuestion =question as FillQuestion
                                questionText=fillQuestion.sentenceBefore+"___"+fillQuestion.sentenceAfter
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = questionText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontFamily = AppFonts.quicksand,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

//                            if(question.questionType=="Fill"){
//                                val fillQuestion =question as FillQuestion
//                                Text(
//                                    text = fillQuestion.sentenceBefore+"___"+fillQuestion.sentenceAfter,
//                                    fontSize = 15.sp,
//                                    fontWeight = FontWeight.Normal,
//                                    textAlign = TextAlign.Center,
//                                    fontFamily = AppFonts.quicksand,
//                                    color = Color.Black,
//                                    modifier = Modifier.padding(horizontal = 16.dp)
//                                )
//                            }

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
                        text = floor(secondsRemaining.value).toInt().toString(),
                        color = primaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFonts.quicksand
                    )
                }


            }



        }
//        FillQuestionOptions()
        if(question.questionType=="Fill"){
            FillQuestionOptions(viewModel)
        }
        else{
            MatchQuestionOptions(viewModel)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
           NextButton(viewModel)
        }

//        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun FillQuestionOptions(viewModel: QuestionScreenViewModel) {
    // State to track the selected option by index (initially -1, meaning none is selected)
    var selectedIndex =viewModel.selectedIndex.collectAsState()

    // List of option texts
    var questionBatch=viewModel.questionBatch.collectAsState()
    var currentQuestionIndex=viewModel.currentQuestionIndex.collectAsState()

    var question=questionBatch.value[currentQuestionIndex.value] as FillQuestion
    val options = question.options
    val isTimerRunning = viewModel.isTimerRunning.collectAsState()
//    LaunchedEffect(key1=isTimerRunning.value) {
//        selectedIndex=-1
//    }



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
                isSelected = selectedIndex.value == index, // Check if this option is selected
                onClick = {
                    viewModel.setSelectedIndex(index) // Update selected index when clicked
                    viewModel.setSelectedWord(options[index])
                },
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between options
        }
    }
}

@Composable
fun MatchQuestionOptions(viewModel: QuestionScreenViewModel) {
    val questionBatch = viewModel.questionBatch.collectAsState()
    val currentQuestionIndex = viewModel.currentQuestionIndex.collectAsState()

    val question = questionBatch.value[currentQuestionIndex.value] as MatchQuestion

    val translatedWords: List<String> = question.wordPairs.keys.toList()
    val originalWords: List<String> = question.wordPairs.values.toList()

    val currentFirstWord = viewModel.currentFirstWord.collectAsState()
    val currentSecondWord = viewModel.currentSecondWord.collectAsState()
    val isOriginalWordSelected = viewModel.isOriginalWordSelected.collectAsState()
    val isTranslatedWordSelected = viewModel.isTranslatedWordSelected.collectAsState()
    val selectedPairsCount = viewModel.selectedPairsCount.collectAsState()
    val isTimerRunning = viewModel.isTimerRunning.collectAsState()

    val wordMapState = viewModel.wordMap.collectAsState()
    val colorMap = viewModel.colorMap.collectAsState()
    val currentSelectedColor = viewModel.currentSelectedColor.collectAsState()
    val resetTrigger = viewModel.resetTrigger.collectAsState()

    var isLoading by remember { mutableStateOf(true) }
    var isOperationInProgress by remember { mutableStateOf(false) }
    var resetKey by remember { mutableStateOf(0) }

    LaunchedEffect(currentQuestionIndex.value, resetTrigger.value) {
        Log.d("MatchQuestion", "LaunchedEffect triggered - Index: ${currentQuestionIndex.value}, ResetTrigger: ${resetTrigger.value}")

        isLoading = true

        viewModel.initializeWordMapForMatchQuestion()

        Log.d("MatchQuestion", "Reset completed for question ${currentQuestionIndex.value}")

        isLoading = false
    }

    LaunchedEffect(wordMapState.value) {
        if (wordMapState.value.isEmpty()) {
            resetKey++
        }
    }

//    Log.e(viewModel.TAG, wordMapState.value.toString())

    if (isLoading) {
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
                        isSelected = wordMapState.value[translatedWords[i]]!!.isNotEmpty() || currentFirstWord.value == translatedWords[i],
                        color = colorMap.value[translatedWords[i]] ?: (if (currentFirstWord.value == translatedWords[i]) currentSelectedColor.value!! else Color.Transparent),
                        isDimmed = currentFirstWord.value.isNotEmpty() && currentFirstWord.value != translatedWords[i] && isTranslatedWordSelected.value,
                        onClick = {
                            if (!isOperationInProgress) {
                                isOperationInProgress = true
                                viewModel.handleWordSelection(translatedWords[i], true)
                                isOperationInProgress = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    MatchOptionBox(
                        text = originalWords[i],
                        isSelected = wordMapState.value[originalWords[i]]!!.isNotEmpty() || currentFirstWord.value == originalWords[i],
                        color = colorMap.value[originalWords[i]] ?: (if (currentFirstWord.value == originalWords[i]) currentSelectedColor.value!! else Color.Transparent),
                        isDimmed = currentFirstWord.value.isNotEmpty() && currentFirstWord.value != originalWords[i] && isOriginalWordSelected.value,
                        onClick = {
                            if (!isOperationInProgress) {
                                isOperationInProgress = true
                                viewModel.handleWordSelection(originalWords[i], false)
                                isOperationInProgress = false
                            }
                        }
                    )
                }
            }
        }
    }
}

//
//fun handleWordSelection(
//    selectedWord: String,
//    isTranslated: Boolean,
//    currentFirstWord: String,
//    currentSecondWord: String,
//    isOriginalWordSelected: Boolean,
//    isTranslatedWordSelected: Boolean,
//    wordMapState: State<Map<String, String>>,
//    colorMap: MutableMap<String, Color>,
//    colorQueue: Queue<Color>,
//    currentSelectedColor: Color?,
//    onUpdateCurrentWords: (String, String) -> Unit,
//    onUpdateSelectionFlags: (Boolean, Boolean) -> Unit,
//    onUpdateSelectedPairsCount: (Int) -> Unit,
//    onUpdateCurrentSelectedColor: (Color?) -> Unit,
//    viewModel: QuestionScreenViewModel,
//    translatedWords: List<String>
//) {
//    when {
//        // Case 1: Selecting the first word of a pair
//        currentFirstWord.isEmpty() && (wordMapState.value[selectedWord]?.isEmpty() == true) -> {
//            val newColor = colorQueue.poll() ?: Color.Gray
//            colorMap[selectedWord] = newColor
//            onUpdateCurrentWords(selectedWord, "")
//            onUpdateSelectionFlags(!isTranslated, isTranslated)
//            onUpdateCurrentSelectedColor(newColor)
//        }
//        // Case 2: Selecting the second word of a pair
//        currentSecondWord.isEmpty() && ((isTranslated && isOriginalWordSelected) || (!isTranslated && isTranslatedWordSelected)) -> {
//            if (wordMapState.value[selectedWord] != "") {
//                viewModel.clearWordPair(selectedWord)
//                colorMap[selectedWord]?.let { colorQueue.offer(it) }
//                colorMap.remove(selectedWord)
//                colorMap.remove(wordMapState.value[selectedWord])
//            }
//
//            viewModel.updateWordMap(currentFirstWord, selectedWord)
//            currentSelectedColor?.let {
//                colorMap[currentFirstWord] = it
//                colorMap[selectedWord] = it
//            }
//
//            if (colorQueue.isEmpty()) {
//                viewModel.setWordPairs(wordMapState.value.filter { it.value.isNotEmpty() }.toMap())
//            }
//
//            onUpdateCurrentWords("", "")
//            onUpdateSelectionFlags(false, false)
//            onUpdateSelectedPairsCount(colorMap.size / 2)
//            onUpdateCurrentSelectedColor(null)
//        }
//        // Case 3: Deselecting a word
//        selectedWord == currentFirstWord || (wordMapState.value[selectedWord]?.isNotEmpty() == true && currentFirstWord.isEmpty()) -> {
//            viewModel.clearWordPair(selectedWord)
//            colorMap[selectedWord]?.let { colorQueue.offer(it) }
//            colorMap.remove(selectedWord)
//            colorMap.remove(wordMapState.value[selectedWord])
//            onUpdateCurrentWords("", "")
//            onUpdateSelectionFlags(false, false)
//            onUpdateSelectedPairsCount(colorMap.size / 2)
//            onUpdateCurrentSelectedColor(null)
//        }
//    }
//}

@Composable
fun OptionBox(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
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
@Composable
fun MatchOptionBox(
    text: String,
    isSelected: Boolean,
    color: Color,
    isDimmed: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp, 42.dp)
            .shadow(4.dp, shape = RoundedCornerShape(9.dp), clip = false)
            .background(
                color = if (isSelected) color else boxBackground,
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
    }
}

/**
 * Adds a drop shadow effect to the composable.
 *
 * This modifier allows you to draw a shadow behind the composable with various customization options.
 *
 * @param shape The shape of the shadow.
 * @param color The color of the shadow.
 * @param blur The blur radius of the shadow
 * @param offsetY The shadow offset along the Y-axis.
 * @param offsetX The shadow offset along the X-axis.
 * @param spread The amount to increase the size of the shadow.
 *
 * @return A new `Modifier` with the drop shadow effect applied.
 */
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.doubleShadowDrop(
    shape: Shape,
    offset: Dp = 4.dp,
    blur: Dp = 8.dp
) = this
    .dropShadow(shape, Color.Black.copy(0.25f), blur, offset, offset)
    .dropShadow(shape, Color.White.copy(0.25f), blur, -offset, -offset)

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

@Composable
fun NextButton(viewModel: QuestionScreenViewModel) {
    val isSolved=viewModel.isSolved.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColor = Color(0xFF00CED1)
    val shadowColor = Color(0xFF008B8B)  // Darker shade for shadow
    val shape =RoundedCornerShape(10.dp)


    // Animate shadow offset and blur radius
    // To create a hide shadow animation on press
    val shadowOffset by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp
    )
    val shadowBlur by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 8.dp
    )


    Box(
        modifier = Modifier
            .size(50.dp)
            .doubleShadowDrop(shape, shadowOffset, shadowBlur)
//            .offset(y = if (isPressed) 2.dp else 0.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(buttonColor, shape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = {
                    viewModel.onQuestionAnswered()
                },
                enabled = isSolved.value
            ),

//            .alpha(if (isSolved.value) 1f else 0.5f),
        contentAlignment = Alignment.Center,

    ) {
        Image(
            painter = painterResource(id = R.drawable.next_arrow),
            contentDescription = "Next Button",
            modifier = Modifier
                .size(35.dp)
                .alpha(if (isSolved.value) 1f else 0.5f)
        )
    }
}



