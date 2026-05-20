package com.example.temiepmspresenter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = EpmsPrimary,
    secondary = EpmsSecondary,
    tertiary = SportsColor,
    background = EpmsBackground,
    surface = Color.White,
    surfaceVariant = Color(0xFFF0F4F8),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFF5A5A5A)
)

private val DarkColorScheme = darkColorScheme(
    primary = EpmsPrimaryDark,
    secondary = EpmsSecondary,
    tertiary = SportsColor,
    background = EpmsBackgroundDark,
    surface = EpmsSurfaceDark,
    surfaceVariant = EpmsSurfaceVariantDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE8EAF6),
    onSurface = Color(0xFFE8EAF6),
    onSurfaceVariant = Color(0xFFB0B4CA)
)

@Composable
fun TemiEPMSPresenterTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
