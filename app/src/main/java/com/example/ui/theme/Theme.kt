package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = EmeraldGreenPrimary,
    onPrimary = OnEmeraldGreenPrimary,
    primaryContainer = EmeraldGreenContainer,
    onPrimaryContainer = EmeraldGreenDark,
    secondary = GoldSecondary,
    onSecondary = OnGoldSecondary,
    secondaryContainer = GoldSecondaryContainer,
    onSecondaryContainer = GoldSecondaryDark,
    tertiary = AccentTeal,
    tertiaryContainer = AccentTealContainer,
    background = SurfaceLight,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = EmeraldGreenLight,
    onSurfaceVariant = TextSecondary,
    outline = BorderLight
)

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldGreenContainer,
    onPrimary = EmeraldGreenDark,
    primaryContainer = EmeraldGreenPrimary,
    onPrimaryContainer = OnEmeraldGreenPrimary,
    secondary = GoldSecondaryContainer,
    onSecondary = GoldSecondaryDark,
    secondaryContainer = GoldSecondary,
    onSecondaryContainer = OnGoldSecondary,
    tertiary = AccentTealContainer,
    background = DarkSurface,
    onBackground = DarkTextPrimary,
    surface = DarkSurfaceCard,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceCard,
    onSurfaceVariant = DarkTextSecondary
)

@Composable
fun MTsTuanDiahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
