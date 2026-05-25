package com.example.temiepmspresenter.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temiepmspresenter.data.Course
import com.example.temiepmspresenter.data.DynamicAnswer
import com.example.temiepmspresenter.data.ESTIMATED_TOTAL_QUESTIONS
import com.example.temiepmspresenter.data.RESULTS_KEY
import com.example.temiepmspresenter.data.START_QUESTION_KEY
import com.example.temiepmspresenter.data.calculateRecommendedCoursesFromScores
import com.example.temiepmspresenter.data.dynamicQuestions
import com.example.temiepmspresenter.ui.theme.EpmsPrimary
import com.example.temiepmspresenter.ui.theme.EpmsSecondary
import com.example.temiepmspresenter.ui.theme.categoryColor

@Composable
fun CourseFinderScreen(
    courses: List<Course>,
    onBack: () -> Unit,
    onCourseSelected: (Course) -> Unit
) {
    // ── State ────────────────────────────────────────────────────────────────
    var currentQuestionKey by remember { mutableStateOf(START_QUESTION_KEY) }
    val questionHistory = remember { mutableStateListOf<String>() }
    val scoreHistory = remember { mutableStateListOf<Map<String, Int>>() }
    val cumulativeScores = remember { mutableStateMapOf<String, Int>() }
    var showResults by remember { mutableStateOf(false) }
    var recommendedCourses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var navigatingForward by remember { mutableStateOf(true) }

    val currentQuestion = dynamicQuestions[currentQuestionKey]
    val questionsAnswered = questionHistory.size
    val canGoBack = questionHistory.isNotEmpty() || showResults

    // ── Navigation helpers ───────────────────────────────────────────────────
    fun subtractLastScores() {
        if (scoreHistory.isEmpty()) return
        val lastScores = scoreHistory.removeAt(scoreHistory.lastIndex)
        lastScores.forEach { (id, score) ->
            val updated = (cumulativeScores[id] ?: 0) - score
            if (updated <= 0) cumulativeScores.remove(id) else cumulativeScores[id] = updated
        }
    }

    fun goToPreviousQuestion() {
        navigatingForward = false
        if (showResults) {
            showResults = false
            subtractLastScores()
            if (questionHistory.isNotEmpty()) {
                currentQuestionKey = questionHistory.removeAt(questionHistory.lastIndex)
            }
        } else if (questionHistory.isNotEmpty()) {
            subtractLastScores()
            currentQuestionKey = questionHistory.removeAt(questionHistory.lastIndex)
        }
    }

    fun answerQuestion(answer: DynamicAnswer) {
        navigatingForward = true
        questionHistory.add(currentQuestionKey)
        scoreHistory.add(answer.courseScores)
        answer.courseScores.forEach { (id, score) ->
            cumulativeScores[id] = (cumulativeScores[id] ?: 0) + score
        }
        if (answer.nextQuestionKey == RESULTS_KEY) {
            recommendedCourses = calculateRecommendedCoursesFromScores(cumulativeScores.toMap(), courses)
            showResults = true
        } else {
            currentQuestionKey = answer.nextQuestionKey
        }
    }

    // ── UI ───────────────────────────────────────────────────────────────────
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(EpmsPrimary, EpmsSecondary)))
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                // Left — previous question (or home if at Q1)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.18f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { if (canGoBack) goToPreviousQuestion() else onBack() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (canGoBack) "Pergunta anterior" else "Voltar ao início",
                            tint = if (canGoBack) Color.White else Color.White.copy(alpha = 0.45f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Center — title
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🧭", fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Descobre o teu Curso",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = if (showResults) "Os teus resultados"
                               else "Pergunta ${questionsAnswered + 1} de $ESTIMATED_TOTAL_QUESTIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                // Right — home
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.18f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Voltar ao início",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (!showResults) {
                // Progress bar
                LinearProgressIndicator(
                    progress = { questionsAnswered.toFloat() / ESTIMATED_TOTAL_QUESTIONS },
                    modifier = Modifier.fillMaxWidth(),
                    color = EpmsSecondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                // Question content — slides in/out based on direction
                AnimatedContent(
                    targetState = currentQuestionKey,
                    transitionSpec = {
                        if (navigatingForward) {
                            (slideInHorizontally(tween(280)) { it } + fadeIn(tween(200))).togetherWith(
                                slideOutHorizontally(tween(220)) { -it } + fadeOut(tween(150))
                            )
                        } else {
                            (slideInHorizontally(tween(280)) { -it } + fadeIn(tween(200))).togetherWith(
                                slideOutHorizontally(tween(220)) { it } + fadeOut(tween(150))
                            )
                        }
                    },
                    label = "questionTransition"
                ) { qKey ->
                    val question = dynamicQuestions[qKey] ?: return@AnimatedContent
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Question card
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = question.emoji, fontSize = 44.sp)
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = question.question,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 26.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Answer options
                        question.answers.forEachIndexed { index, answer ->
                            AnswerButton(
                                text = answer.text,
                                answerIndex = index,
                                onClick = { answerQuestion(answer) }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                ResultsScreen(
                    recommendedCourses = recommendedCourses,
                    onCourseSelected = onCourseSelected,
                    onBack = { goToPreviousQuestion() },
                    onRetry = {
                        currentQuestionKey = START_QUESTION_KEY
                        questionHistory.clear()
                        scoreHistory.clear()
                        cumulativeScores.clear()
                        showResults = false
                        recommendedCourses = emptyList()
                        navigatingForward = true
                    }
                )
            }
        }
    }
}

private val answerAccentColors = listOf(
    Color(0xFF1976D2),
    Color(0xFF388E3C),
    Color(0xFFF57C00),
    Color(0xFF7B1FA2),
    Color(0xFFD32F2F)
)

@Composable
fun AnswerButton(text: String, answerIndex: Int, onClick: () -> Unit) {
    val accentColor = answerAccentColors[answerIndex % answerAccentColors.size]
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp, pressedElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(accentColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ('A' + answerIndex).toChar().toString(),
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ResultsScreen(
    recommendedCourses: List<Course>,
    onCourseSelected: (Course) -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 3 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "🎉", fontSize = 52.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Os teus cursos ideais!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Com base nas tuas respostas, aqui estão as nossas sugestões personalizadas. Toca num curso para saberes mais!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        recommendedCourses.forEachIndexed { index, course ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(400, delayMillis = 200 + index * 150)) +
                        slideInVertically(tween(400, delayMillis = 200 + index * 150)) { it / 2 }
            ) {
                RecommendedCourseCard(
                    course = course,
                    isTopPick = index == 0,
                    onClick = { onCourseSelected(course) }
                )
            }
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 650))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                TextButton(onClick = onBack) {
                    Text("← Última pergunta")
                }
                OutlinedButton(
                    onClick = onRetry,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("🔄  Repetir")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecommendedCourseCard(course: Course, isTopPick: Boolean, onClick: () -> Unit) {
    val cardColor = categoryColor(course.category)
    val darkerColor = Color(
        red = (cardColor.red * 0.66f).coerceIn(0f, 1f),
        green = (cardColor.green * 0.66f).coerceIn(0f, 1f),
        blue = (cardColor.blue * 0.66f).coerceIn(0f, 1f),
        alpha = 1f
    )

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isTopPick) 8.dp else 4.dp,
            pressedElevation = 2.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(cardColor, darkerColor)))
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isTopPick) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White.copy(alpha = 0.22f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⭐", fontSize = 17.sp)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.White.copy(alpha = 0.20f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = course.icon, fontSize = 26.sp)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    if (isTopPick) {
                        Text(
                            text = "Melhor correspondência",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.85f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = course.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = course.shortDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "→",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
