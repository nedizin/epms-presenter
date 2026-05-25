package com.example.temiepmspresenter.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.temiepmspresenter.data.Course
import com.example.temiepmspresenter.ui.theme.EpmsPrimary
import com.example.temiepmspresenter.ui.theme.EpmsSecondary
import com.example.temiepmspresenter.ui.theme.categoryColor
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    courses: List<Course>,
    onCourseSelected: (Course) -> Unit,
    onCreditsClick: () -> Unit,
    onNavigateToCourseFinder: () -> Unit = {},
    isDarkTheme: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    val animatedBg by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.background,
        animationSpec = tween(450, easing = FastOutSlowInEasing),
        label = "bgColor"
    )

    Scaffold(containerColor = animatedBg) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header — extends behind status bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(EpmsPrimary, EpmsSecondary)
                            )
                        )
                        .statusBarsPadding()
                        .padding(vertical = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Decorative circles
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 60.dp, y = (-60).dp)
                            .background(Color.White.copy(alpha = 0.06f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = (-35).dp, y = 35.dp)
                            .background(Color.White.copy(alpha = 0.06f), CircleShape)
                    )

                    // Night mode toggle with animated icon
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.18f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = onToggleTheme,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AnimatedContent(
                                targetState = isDarkTheme,
                                transitionSpec = {
                                    (scaleIn(tween(220)) + fadeIn(tween(220))).togetherWith(
                                        scaleOut(tween(160)) + fadeOut(tween(160))
                                    )
                                },
                                label = "themeIcon"
                            ) { dark ->
                                Icon(
                                    imageVector = if (dark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                    contentDescription = if (dark) "Modo claro" else "Modo noturno",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // Logo
                    Box(
                        modifier = Modifier
                            .height(72.dp)
                            .wrapContentWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https://epms.pt/images/upload/312fb44ba5d29ec7961c715f6e539bf0.png",
                            contentDescription = "Logo EPMS",
                            modifier = Modifier.height(60.dp).wrapContentWidth(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                }

                // Course grid — bottom padding accounts for nav bar + FAB
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 210.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(count = courses.size, key = { courses[it].id }) { index ->
                        val course = courses[index]
                        AnimatedCourseCard(
                            course = course,
                            index = index,
                            onClick = { onCourseSelected(course) }
                        )
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Créditos",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFBDBDBD),
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable { onCreditsClick() }
                            )
                            Text(
                                text = "© Escola Profissional Mariana Seixas 2026 – G1 3EAC",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFBDBDBD),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Chat overlay — floats over the grid at bottom-left
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomStart),
                contentAlignment = Alignment.BottomStart
            ) {
                ChatOverlay(onNavigateToCourseFinder = onNavigateToCourseFinder)
            }

            // Course Finder FAB — bottom-right shortcut
            FloatingActionButton(
                onClick = onNavigateToCourseFinder,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 16.dp
                    ),
                containerColor = EpmsSecondary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 3.dp
                ),
                shape = CircleShape
            ) {
                Text("🧭", fontSize = 22.sp)
            }
        }
    }
}

@Composable
fun AnimatedCourseCard(course: Course, index: Int, onClick: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index.coerceAtMost(11) * 65L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(380)) +
                slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    initialOffsetY = { it / 2 }
                )
    ) {
        PressableCourseCard(course = course, onClick = onClick)
    }
}

@Composable
fun PressableCourseCard(course: Course, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "cardScale"
    )

    Box(modifier = Modifier.scale(scale)) {
        CourseCard(course = course, onClick = onClick, interactionSource = interactionSource)
    }
}

@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val cardColor = categoryColor(course.category)
    val darkerColor = Color(
        red = (cardColor.red * 0.66f).coerceIn(0f, 1f),
        green = (cardColor.green * 0.66f).coerceIn(0f, 1f),
        blue = (cardColor.blue * 0.66f).coerceIn(0f, 1f),
        alpha = 1f
    )

    Card(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .height(215.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = listOf(cardColor, darkerColor))
                )
        ) {
            // Decorative depth circles
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 35.dp, y = (-35).dp)
                    .background(Color.White.copy(alpha = 0.10f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-22).dp, y = 22.dp)
                    .background(Color.White.copy(alpha = 0.08f), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon in frosted circle
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .background(Color.White.copy(alpha = 0.20f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = course.icon, fontSize = 30.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = course.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.87f),
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = "Toca para saber mais  →",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.60f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
