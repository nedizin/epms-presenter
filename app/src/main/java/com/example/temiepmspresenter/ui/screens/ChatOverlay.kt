package com.example.temiepmspresenter.ui.screens

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temiepmspresenter.data.ChatMessage
import com.example.temiepmspresenter.data.ChatOption
import com.example.temiepmspresenter.data.chatOptions
import com.example.temiepmspresenter.ui.theme.EpmsPrimary
import com.example.temiepmspresenter.ui.theme.EpmsSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ChatOverlay(onNavigateToCourseFinder: () -> Unit = {}) {
    var isOpen by remember { mutableStateOf(false) }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var isTyping by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val context = LocalContext.current
    var chatTts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isChatSpeaking by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                engine?.setLanguage(Locale("pt", "PT"))
                engine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) { isChatSpeaking = true }
                    override fun onDone(utteranceId: String?) { isChatSpeaking = false }
                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) { isChatSpeaking = false }
                })
                chatTts = engine
            }
        }
        onDispose {
            engine?.stop()
            engine?.shutdown()
        }
    }

    val lastBotText = messages.lastOrNull { it.isBot }?.text

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Chat panel — slides up above the FAB
        AnimatedVisibility(
            visible = isOpen,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
                initialOffsetY = { it }
            ) + fadeIn(tween(180)),
            exit = slideOutVertically(
                animationSpec = tween(180),
                targetOffsetY = { it }
            ) + fadeOut(tween(140)),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 76.dp)
                .width(420.dp)
                .heightIn(max = 580.dp)
        ) {
            ChatPanel(
                messages = messages,
                isTyping = isTyping,
                listState = listState,
                isSpeaking = isChatSpeaking,
                onClose = {
                    isOpen = false
                    chatTts?.stop()
                    isChatSpeaking = false
                },
                onToggleSpeech = {
                    if (isChatSpeaking) {
                        chatTts?.stop()
                        isChatSpeaking = false
                    } else {
                        lastBotText?.let {
                            chatTts?.speak(it.stripForSpeech(), TextToSpeech.QUEUE_FLUSH, null, "chat_msg")
                        }
                    }
                },
                onOptionSelected = { option ->
                    scope.launch {
                        messages.add(ChatMessage(option.question, isBot = false))
                        listState.animateScrollToItem(messages.lastIndex)
                        isTyping = true
                        delay(1100)
                        isTyping = false
                        messages.add(ChatMessage(option.answer, isBot = true))
                        delay(80)
                        listState.animateScrollToItem(messages.lastIndex)
                    }
                }
            )
        }

        // FAB — always at bottom-left
        FloatingActionButton(
            onClick = {
                isOpen = !isOpen
                if (!isOpen) {
                    chatTts?.stop()
                    isChatSpeaking = false
                }
                if (isOpen && messages.isEmpty()) {
                    scope.launch {
                        delay(350)
                        messages.add(
                            ChatMessage(
                                "Olá! 👋 Sou o assistente da EPMS. Escolhe uma pergunta para saberes mais sobre a nossa escola!",
                                isBot = true
                            )
                        )
                    }
                }
            },
            modifier = Modifier.align(Alignment.BottomStart),
            containerColor = EpmsPrimary,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 3.dp
            ),
            shape = CircleShape
        ) {
            AnimatedContent(
                targetState = isOpen,
                transitionSpec = {
                    (scaleIn(tween(180)) + fadeIn(tween(180))).togetherWith(
                        scaleOut(tween(140)) + fadeOut(tween(120))
                    )
                },
                label = "fabIcon"
            ) { open ->
                if (open) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Fechar chat",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(text = "💬", fontSize = 24.sp)
                }
            }
        }
    }
}

@Composable
fun ChatPanel(
    messages: List<ChatMessage>,
    isTyping: Boolean,
    listState: LazyListState,
    isSpeaking: Boolean,
    onClose: () -> Unit,
    onToggleSpeech: () -> Unit,
    onOptionSelected: (ChatOption) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(EpmsPrimary, EpmsSecondary)
                        ),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🤖", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Assistente EPMS",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color(0xFF69F0AE), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Online",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.80f)
                            )
                        }
                    }
                    // Speaker button
                    IconButton(
                        onClick = onToggleSpeech,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                            contentDescription = if (isSpeaking) "Parar áudio" else "Ouvir resposta",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Fechar",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Message list
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 370.dp),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message = message)
                }
                if (isTyping) {
                    item { TypingBubble() }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

            // Predefined questions
            Text(
                text = "Perguntas frequentes",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 14.dp, top = 8.dp, bottom = 2.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                items(chatOptions) { option ->
                    SuggestionChip(
                        onClick = { onOptionSelected(option) },
                        label = {
                            Text(
                                text = option.question,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val isBot = message.isBot
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
    ) {
        if (isBot) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(EpmsPrimary.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("🤖", fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

        Card(
            shape = RoundedCornerShape(
                topStart = if (isBot) 4.dp else 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = if (isBot) 16.dp else 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isBot) MaterialTheme.colorScheme.surfaceVariant else EpmsPrimary
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodySmall,
                color = if (isBot) MaterialTheme.colorScheme.onSurface else Color.White,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
fun TypingBubble() {
    val transition = rememberInfiniteTransition(label = "typing")

    val alpha1 by transition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.3f at 0; 1f at 150; 0.3f at 350; 0.3f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "d1"
    )
    val alpha2 by transition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.3f at 200; 1f at 350; 0.3f at 550; 0.3f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "d2"
    )
    val alpha3 by transition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.3f at 400; 1f at 550; 0.3f at 750; 0.3f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "d3"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(EpmsPrimary.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("🤖", fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Card(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(alpha1, alpha2, alpha3).forEach { alpha ->
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(EpmsPrimary.copy(alpha = alpha), CircleShape)
                    )
                }
            }
        }
    }
}
