package com.example.temiepmspresenter.ui.screens

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temiepmspresenter.data.Course
import com.example.temiepmspresenter.data.CurriculumComponent
import com.example.temiepmspresenter.ui.theme.categoryColor
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun CourseDetailScreen(course: Course, onBack: () -> Unit) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isSpeaking by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                engine?.setLanguage(Locale("pt", "PT"))
                engine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) { isSpeaking = true }
                    override fun onDone(utteranceId: String?) { isSpeaking = false }
                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) { isSpeaking = false }
                })
                tts = engine
            }
        }
        onDispose {
            engine?.stop()
            engine?.shutdown()
        }
    }

    // Pulse animation for speaker button
    val pulseTransition = rememberInfiniteTransition(label = "speakerPulse")
    val pulseAlpha by pulseTransition.animateFloat(
        initialValue = 0.55f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "pulseAlpha"
    )
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1f, targetValue = 1.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "pulseScale"
    )

    val accentColor = categoryColor(course.category)
    val darkerAccent = Color(
        red = (accentColor.red * 0.65f).coerceIn(0f, 1f),
        green = (accentColor.green * 0.65f).coerceIn(0f, 1f),
        blue = (accentColor.blue * 0.65f).coerceIn(0f, 1f),
        alpha = 1f
    )

    val backgroundColor = MaterialTheme.colorScheme.background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // ── Hero ──────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(accentColor, darkerAccent),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                .statusBarsPadding()
        ) {
            // Layered decorative circles for depth
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 70.dp, y = (-70).dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 30.dp)
                    .background(Color.White.copy(alpha = 0.07f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-45).dp, y = 45.dp)
                    .background(Color.White.copy(alpha = 0.06f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-20).dp)
                    .background(Color.White.copy(alpha = 0.04f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-30).dp, y = (-20).dp)
                    .background(Color.White.copy(alpha = 0.08f), CircleShape)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                // ── Nav bar ──────────────────────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    // Back pill
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(20.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Voltar",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Speaker button with pulse ring
                    Box(contentAlignment = Alignment.Center) {
                        if (isSpeaking) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .graphicsLayer {
                                        scaleX = pulseScale
                                        scaleY = pulseScale
                                        alpha = pulseAlpha
                                    }
                                    .background(Color.White.copy(alpha = 0.40f), CircleShape)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(
                                    Color.White.copy(alpha = if (isSpeaking) 0.30f else 0.18f),
                                    CircleShape
                                )
                        ) {
                            IconButton(
                                onClick = {
                                    if (isSpeaking) {
                                        tts?.stop()
                                        isSpeaking = false
                                    } else {
                                        tts?.speak(
                                            buildCourseTranscript(course),
                                            TextToSpeech.QUEUE_FLUSH,
                                            null,
                                            "course_${course.id}"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = if (isSpeaking) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                                    contentDescription = if (isSpeaking) "Parar áudio" else "Ouvir apresentação",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }

                // ── Icon + title + subtitle ──────────────────────────────────
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 12.dp, bottom = 16.dp)
                ) {
                    // 3-ring glowing icon
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(128.dp)
                                .background(Color.White.copy(alpha = 0.08f), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(108.dp)
                                .background(Color.White.copy(alpha = 0.13f), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .background(Color.White.copy(alpha = 0.24f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = course.icon, fontSize = 46.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = if (course.fullName != course.name) course.fullName else course.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 34.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = course.shortDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.82f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }

                // ── Info chips strip ─────────────────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeroInfoChip(course.level)
                    Spacer(modifier = Modifier.width(8.dp))
                    HeroInfoChip(course.equivalency)
                    course.totalHours?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        HeroInfoChip("⏱ $it")
                    }
                }
            }
        }

        // ── Content sections ──────────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                AnimatedSection(delayMs = 0) {
                    SectionCard(
                        title = "Descrição do Curso",
                        icon = Icons.Filled.MenuBook,
                        accentColor = accentColor
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "❝",
                                fontSize = 72.sp,
                                color = accentColor.copy(alpha = 0.08f),
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.align(Alignment.TopStart)
                            )
                            Text(
                                text = course.fullDescription,
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 27.sp,
                                modifier = Modifier.padding(top = 10.dp, start = 2.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            if (course.competencies.isNotEmpty()) {
                item {
                    AnimatedSection(delayMs = 100) {
                        SectionCard(
                            title = "Competências",
                            icon = Icons.Filled.Star,
                            accentColor = accentColor
                        ) {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                course.competencies.forEach { competency ->
                                    Row(
                                        modifier = Modifier
                                            .background(
                                                accentColor.copy(alpha = 0.10f),
                                                RoundedCornerShape(9.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(7.dp)
                                                .background(accentColor, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = competency,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = accentColor.copy(alpha = 0.90f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (course.components.isNotEmpty()) {
                item {
                    AnimatedSection(delayMs = 200) {
                        SectionCard(
                            title = "Plano Curricular",
                            icon = Icons.Filled.School,
                            accentColor = accentColor
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                course.components.forEachIndexed { index, component ->
                                    CurriculumRow(component = component, accentColor = accentColor)
                                    if (index < course.components.lastIndex) {
                                        HorizontalDivider(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (course.careerPaths.isNotEmpty()) {
                item {
                    AnimatedSection(delayMs = 300) {
                        SectionCard(
                            title = "Saídas Profissionais",
                            icon = Icons.Filled.Work,
                            accentColor = accentColor
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                course.careerPaths.forEach { path ->
                                    CareerPathRow(text = path, accentColor = accentColor)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    text = "© Escola Profissional Mariana Seixas 2026 – G1 3EAC",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFBDBDBD),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
            }
        }
    }
}

private fun buildCourseTranscript(course: Course): String {
    val displayName = if (course.fullName != course.name) course.fullName else course.name
    return buildString {
        append("$displayName. ")
        append("${course.fullDescription} ")
        if (course.competencies.isNotEmpty()) {
            append("Neste curso, vais desenvolver competências como: ")
            append(course.competencies.joinToString(", "))
            append(". ")
        }
        if (course.careerPaths.isNotEmpty()) {
            append("As principais saídas profissionais são: ")
            append(course.careerPaths.joinToString(", "))
            append(". ")
        }
        append("Para mais informações, visita o site da EPMS em epms ponto pt.")
    }.stripForSpeech()
}

internal fun String.stripForSpeech(): String {
    val sb = StringBuilder()
    var i = 0
    while (i < length) {
        val cp = codePointAt(i)
        if (!isEmojiCodePoint(cp)) sb.appendCodePoint(cp)
        i += Character.charCount(cp)
    }
    return sb.toString()
        .replace('\n', ' ')
        .replace(Regex("\\s{2,}"), " ")
        .trim()
}

private fun isEmojiCodePoint(cp: Int): Boolean =
    cp in 0x1F600..0x1F64F ||  // emoticons
    cp in 0x1F300..0x1F5FF ||  // misc symbols & pictographs
    cp in 0x1F680..0x1F6FF ||  // transport & map
    cp in 0x1F700..0x1F77F ||  // alchemical
    cp in 0x1F780..0x1F7FF ||  // geometric shapes extended
    cp in 0x1F800..0x1F8FF ||  // supplemental arrows-c
    cp in 0x1F900..0x1F9FF ||  // supplemental symbols
    cp in 0x1FA00..0x1FAFF ||  // symbols & pictographs extended-a
    cp in 0x2300..0x23FF   ||  // misc technical
    cp in 0x2600..0x27BF   ||  // misc symbols & dingbats
    cp in 0x2B00..0x2BFF   ||  // misc symbols & arrows
    cp in 0x1F1E0..0x1F1FF ||  // regional indicators (flags)
    cp == 0x200D           ||  // zero-width joiner
    cp in 0xFE00..0xFE0F       // variation selectors

// ── Hero chip ─────────────────────────────────────────────────────────────────

@Composable
private fun HeroInfoChip(label: String) {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ── Section animation wrapper ─────────────────────────────────────────────────

@Composable
fun AnimatedSection(delayMs: Int, content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delayMs.toLong())
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 400)) +
                slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    initialOffsetY = { it / 3 }
                )
    ) {
        content()
    }
}

// ── Section card with gradient header ────────────────────────────────────────

@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    val headerDark = Color(
        red = (accentColor.red * 0.72f).coerceIn(0f, 1f),
        green = (accentColor.green * 0.72f).coerceIn(0f, 1f),
        blue = (accentColor.blue * 0.72f).coerceIn(0f, 1f),
        alpha = 1f
    )
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Gradient header band
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(accentColor, headerDark),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, 0f)
                        ),
                        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 13.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.22f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            // Content area
            Column(modifier = Modifier.padding(20.dp)) {
                content()
            }
        }
    }
}

// ── Career path row ───────────────────────────────────────────────────────────

@Composable
private fun CareerPathRow(text: String, accentColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(accentColor.copy(alpha = 0.07f), RoundedCornerShape(11.dp))
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(accentColor.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 20.sp
        )
    }
}

// ── Curriculum row ────────────────────────────────────────────────────────────

@Composable
fun CurriculumRow(component: CurriculumComponent, accentColor: Color = Color(0xFF0D47A1)) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = component.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(accentColor, RoundedCornerShape(20.dp))
                    .padding(horizontal = 13.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${component.hours}h",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        if (component.subjects.isNotEmpty()) {
            Spacer(modifier = Modifier.height(7.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(9.dp))
                    .padding(horizontal = 12.dp, vertical = 9.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                component.subjects.forEach { subject ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(accentColor.copy(alpha = 0.55f), CircleShape)
                        )
                        Spacer(modifier = Modifier.width(9.dp))
                        Text(
                            text = subject,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }
    }
}
